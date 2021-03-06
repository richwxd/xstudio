package io.github.xbeeant.http;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.core.ErrorCodeConstant;
import io.github.xbeeant.core.JsonHelper;
import io.github.xbeeant.http.exception.HttpResponseFailedException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaobiao
 * @version 2020/2/12
 */
@SuppressWarnings("unused")
public class Requests {

    /**
     *
     */
    public static final String CONTENT_TYPE = "Content-Type";
    /**
     * ????????????ID
     */
    public static final String TRACE_ID = "TRACE_ID";
    /**
     * ??????IP
     */
    public static final String UNKNOWN = "unknown";
    /**
     * ??????content ??????
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/json";
    /**
     * ?????????????????????
     */
    private static final int SOCKET_TIMEOUT = HttpClientConfig.getHttpSocketTimeout();
    /**
     * ????????????????????????????????????
     */
    private static final int MAX_PRE_ROUTE = HttpClientConfig.getHttpPerRouteSize();
    /**
     * ???????????????
     */
    private static final int MAX_ROUTE = HttpClientConfig.getHttpMaxPoolSize();
    /**
     * ????????????
     */
    private static final int MAX_CONNECTION = HttpClientConfig.getHttpMaxPoolSize();
    /**
     * ????????????????????????
     */
    private static final int VALIDATE_TIME = 30000;

    private static final String LOCALHOST = "127.0.0.1";

    private static final String USER_AGENT = "User-Agent";

    /**
     * ??????????????????,??????????????????
     */
    private static final Object SYNC_LOCK = new Object();
    /**
     * ??????
     */
    private static final Logger logger = LoggerFactory.getLogger(Requests.class);
    /**
     * ??????
     */
    private static final Map<String, ScheduledExecutorService> MONITOR_EXECUTORS = new HashMap<>();
    /**
     * ??????????????????
     */
    private static final Map<String, PoolingHttpClientConnectionManager> CONNECTION_MANAGER = new HashMap<>();
    /**
     * ??????????????????????????????
     */
    private static final Map<String, CloseableHttpClient> HTTP_CLIENTS = new HashMap<>();

    /**
     * ???????????????
     */
    public static void closeConnectionPool() {
        try {
            Set<Map.Entry<String, CloseableHttpClient>> clients = HTTP_CLIENTS.entrySet();
            for (Map.Entry<String, CloseableHttpClient> client : clients) {
                client.getValue().close();
            }
            Set<Map.Entry<String, PoolingHttpClientConnectionManager>> managers = CONNECTION_MANAGER.entrySet();
            for (Map.Entry<String, PoolingHttpClientConnectionManager> manager : managers) {
                manager.getValue().close();
            }

            Set<Map.Entry<String, ScheduledExecutorService>> executors = MONITOR_EXECUTORS.entrySet();
            for (Map.Entry<String, ScheduledExecutorService> executor : executors) {
                executor.getValue().shutdown();
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param params ??????
     * @param url    ??????
     * @param type   ????????????????????????
     * @param <T>    ????????????????????????
     * @return Msg
     */
    public static <T> ApiResponse<T> formPostProxy(Map<String, String> params, String url, Class<T> type) {
        ApiResponse<T> apiResponse = new ApiResponse<>();

        ClientResponse clientResponse = Requests.postForm(url, params);
        if (null == clientResponse) {
            logger.error("API?????????????????? {} null", url);
            apiResponse.setResult(ErrorCodeConstant.API_INVALID, ErrorCodeConstant.API_INVALID_MSG);
            apiResponse.setMsg("???????????????");
            return apiResponse;
        }

        if (HttpStatus.SC_OK != clientResponse.getOrigin().getStatusLine().getStatusCode()) {
            logger.error("API?????????????????? {} {} {}", url, clientResponse.getOrigin().getStatusLine().getStatusCode(), clientResponse.getContent());
            apiResponse.setResult(ErrorCodeConstant.API_INVALID, ErrorCodeConstant.API_INVALID_MSG);
            apiResponse.setMsg(String.valueOf(clientResponse.getOrigin().getStatusLine().getStatusCode()));
            return apiResponse;
        }

        T data = JsonHelper.toObject(clientResponse.getContent(), type);
        if (null == data) {
            logger.debug("API?????????????????? {} {}", url, clientResponse.getContent());
            apiResponse.setMsg("????????????[" + apiResponse.getCode() + "]" + apiResponse.getMsg());
            apiResponse.setCode(ErrorCodeConstant.API_INVALID);
            return apiResponse;
        }

        apiResponse.setData(apiResponse.getData());
        return apiResponse;
    }

    /**
     * post??????
     *
     * @param url    ??????
     * @param params ??????
     * @return ClientResponse
     */
    public static ClientResponse postForm(String url, Map<String, String> params) {
        HttpPost post = new HttpPost(url);
        //   ???????????????????????????
        List<NameValuePair> nvps = new ArrayList<>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            nvps.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        post.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        return res(post);
    }

    /**
     * res
     *
     * @param method ??????
     * @return {@link ClientResponse}
     */
    private static ClientResponse res(HttpRequestBase method) {
        return res(method, HttpClientConfig.getHttpSocketTimeout());
    }

    /**
     * res
     *
     * @param method ??????
     * @return {@link ClientResponse}
     */
    private static ClientResponse res(HttpRequestBase method, Integer timeout) {
        return res(method, null, timeout);
    }

    /**
     * res
     *
     * @param method ??????
     * @return {@link ClientResponse}
     */
    private static ClientResponse res(HttpRequestBase method, List<Header> headers, Integer timeout) {
        HttpClientContext context = HttpClientContext.create();
        ClientResponse clientResponse = new ClientResponse();

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).build();
        method.setConfig(requestConfig);

        if (!CollectionUtils.isEmpty(headers)) {
            for (Header header : headers) {
                method.addHeader(header);
            }
        }

        if (null == method.getHeaders(TRACE_ID)) {
            method.addHeader(TRACE_ID, UUID.randomUUID().toString());
        }

        try (CloseableHttpResponse response = getHttpClient(method.getURI().toString()).execute(method, context)) {
            // ??????????????????
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                Charset respCharset = ContentType.getOrDefault(entity).getCharset();
                clientResponse.setContent(EntityUtils.toString(entity, respCharset));
                clientResponse.setOrigin(response);
                EntityUtils.consume(entity);
            }
        } catch (Exception cte) {
            logger.error("{} ????????????????????????????????? {}", method.getURI(), cte.getLocalizedMessage(), cte);
            clientResponse = null;
        } finally {
            method.releaseConnection();
        }
        return clientResponse;
    }

    /**
     * ??????HttpClient??????
     *
     * @param url ??????
     * @return {@link CloseableHttpClient}
     */
    public static CloseableHttpClient getHttpClient(String url) {
        String hostname = url.split("/")[2];
        int port = 80;
        if (hostname.contains(":")) {
            String[] arr = hostname.split(":");
            hostname = arr[0];
            port = Integer.parseInt(arr[1]);
        }

        String clientKey = hostname + port;
        CloseableHttpClient client = HTTP_CLIENTS.get(clientKey);
        if (client == null) {
            // ????????????????????????????????????getHttpClient????????????????????????httpClient???????????????,????????????????????????
            synchronized (SYNC_LOCK) {
                client = createHttpClient(MAX_CONNECTION, MAX_PRE_ROUTE, MAX_ROUTE, hostname, port);
                // ??????????????????,????????????????????????????????????
                ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1,
                        new BasicThreadFactory.Builder().namingPattern("http-monitor-schedule-pool-%d").daemon(true).build());
                PoolingHttpClientConnectionManager manager = CONNECTION_MANAGER.get(clientKey);
                executor.scheduleAtFixedRate(new TimerTask() {
                                                 @Override
                                                 public void run() {
                                                     // ??????????????????
                                                     manager.closeExpiredConnections();
                                                     // ??????5s???????????????
                                                     manager.closeIdleConnections(HttpClientConfig.getHttpIdleTimeout(), TimeUnit.MILLISECONDS);
                                                     logger.debug("close expired and idle for over {}s connection", HttpClientConfig.getHttpPerRouteSize());
                                                 }
                                             }
                        , HttpClientConfig.getHttpMonitorInterval()
                        , HttpClientConfig.getHttpMonitorInterval()
                        , TimeUnit.MILLISECONDS);

                HTTP_CLIENTS.put(clientKey, client);
                MONITOR_EXECUTORS.put(clientKey, executor);
            }
        }
        return client;
    }

    /**
     * ??????HttpClient??????
     *
     * @param maxTotal    ???????????????
     * @param maxPerRoute ???????????????????????????
     * @param maxRoute    ???????????????
     * @param hostname    ???????????????
     * @param port        ??????
     * @return {@link CloseableHttpClient}
     */
    public static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, int maxRoute, String hostname, int port) {
        ConnectionSocketFactory plainSoeketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory>create().register("http", plainSoeketFactory)
                .register("https", sslSocketFactory).build();
        String clientKey = hostname + port;
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(registry);
        // ???????????????
        manager.setMaxTotal(maxTotal);
        // ?????????????????????
        manager.setDefaultMaxPerRoute(maxPerRoute);
        // ??????????????????????????????,?????????????????????????????????????????????????????????????????????????????????????????????socket????????????
        manager.setValidateAfterInactivity(VALIDATE_TIME);
        // ??????socket????????????
        SocketConfig config = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
        manager.setDefaultSocketConfig(config);


        HttpHost httpHost = new HttpHost(hostname, port);
        // ???????????????????????????????????????
        manager.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);

        CONNECTION_MANAGER.put(clientKey, manager);
        // ??????????????????
        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
            // ?????????????????????n???????????????
            if (executionCount >= HttpClientConfig.getRetryTimes()) {
                // ????????????n???,????????????
                logger.error("retry has more than {} time, give up request", HttpClientConfig.getRetryTimes());
                return false;
            }
            // ????????????????????????????????????????????????
            if (exception instanceof NoHttpResponseException) {
                // ?????????????????????,?????????????????????????????????,????????????
                logger.error("receive no response from server, retry");
                return true;
            }
            // ????????????SSL????????????
            if (exception instanceof SSLHandshakeException) {
                logger.error("SSL handshake failed");
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                // ????????????
                logger.error("Connection Time out");
                return false;
            }
            // ??????
            if (exception instanceof InterruptedIOException) {
                logger.error("interrupted IO Exception");
                return false;
            }
            // ????????????????????????
            if (exception instanceof UnknownHostException) {
                logger.error("server host unknown");
                return false;
            }
            // SSL????????????
            if (exception instanceof SSLException) {
                logger.error("SSL exception");
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // ??????????????????????????????????????????
            // ???????????????????????????????????????
            return !(request instanceof HttpEntityEnclosingRequest);
        };

        return HttpClients.custom()
                .setConnectionManager(manager)
                .setRetryHandler(httpRequestRetryHandler).build();
    }

    /**
     * ??????HTTP_GET??????
     *
     * @param url    ????????????
     * @param params ????????????
     * @return ????????????????????????
     * 1.??????????????????????????????,????????????
     * 2.?????????????????????????????????????????????,???????????????,???????????????????????????????????????????????????"????????????"?????????
     * 3.????????????????????????,??????????????????????????????,HttpClient?????????????????????Server,?????????????????????????????????
     * ????????????????????????
     * 4.?????????????????????????????????????????????[Content-Type:text/html; charset=GBK]???charset????????????????????????
     * ???????????????
     * 5.????????????????????????Content-Type??????,????????????HttpClient???????????????ISO-8859-1?????????????????????????????????
     * ???
     */
    public static ClientResponse get(String url, Map<String, Object> params) {
        return get(url, params, HttpClientConfig.getHttpSocketTimeout());
    }

    public static ClientResponse get(String url, Map<String, Object> params, Integer timeout) {
        String queryString = getCanonicalQueryString(params);
        if (!url.endsWith("?")) {
            url += "?";
        }

        return get(url + queryString, timeout);
    }

    /**
     * ??????get?????????????????????
     *
     * @param params ????????????
     * @return get????????????
     */
    public static String getCanonicalQueryString(Map<String, Object> params) {
        StringBuilder queryString = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        String value;
        for (Map.Entry<String, Object> entry : entries) {
            queryString.append("&").append(entry.getKey()).append("=");
            if (null != entry.getValue()) {
                try {
                    value = URLEncoder.encode(String.valueOf(entry.getValue()), StandardCharsets.UTF_8.name());
                    if (null != value) {
                        queryString.append(value);
                    }
                } catch (UnsupportedEncodingException e) {
                    logger.error("??????encoding??????", e);
                }
            }
        }

        return queryString.substring(1);
    }

    public static ClientResponse get(String url, Integer timeout) {
        HttpGet get = new HttpGet(url);
        return res(get, timeout);
    }

    /**
     * get??????
     *
     * @param url ????????????
     * @return ClientResponse
     */
    public static ClientResponse get(String url) {
        return get(url, HttpClientConfig.getHttpSocketTimeout());
    }

    public static ClientResponse get(String url, List<Header> headers) {
        return get(url, headers, HttpClientConfig.getHttpSocketTimeout());
    }

    public static ClientResponse get(String url, List<Header> headers, Integer timeout) {
        HttpGet get = new HttpGet(url);
        return res(get, headers, timeout);
    }

    /**
     * ???????????????body
     *
     * @param response {@link HttpResponse}
     * @return ??????body
     */
    public static String getBody(HttpResponse response) {
        // ????????????
        HttpEntity httpEntity = response.getEntity();

        // ????????????
        String body;
        try {
            body = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new HttpResponseFailedException("??????????????????????????????", e);
        }
        return body;
    }

    /**
     * ???????????????Body??????
     *
     * @param request {@link HttpServletRequest}
     * @return body
     * @throws IOException IO??????
     */
    public static String getBody(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * ??????????????????????????????IP
     *
     * @param request {@link HttpServletRequest}
     * @return IP
     */
    public static String getIp(HttpServletRequest request) {
        String serverIp = UNKNOWN;
        try {
            String ip = request.getHeader("X-Forwarded-For");
            if (StringUtils.isEmpty(ip) || serverIp.equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (StringUtils.isEmpty(ip) || serverIp.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || serverIp.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || serverIp.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            int index = ip.indexOf(',');
            if (index != -1) {
                ip = ip.substring(0, index);
            }
            serverIp = "0:0:0:0:0:0:0:1".equals(ip) ? LOCALHOST : ip;
        } catch (Exception e) {
            return UNKNOWN;
        }
        return serverIp;
    }

    /**
     * get server name
     *
     * @param request {@link HttpServletRequest}
     * @return String
     */
    public static String getServerName(HttpServletRequest request) {
        try {
            return request.getHeader(USER_AGENT);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * @param request {@link HttpServletRequest}
     * @return Integer
     */
    public static int getServerPort(HttpServletRequest request) {
        return request.getServerPort();
    }

    /**
     * get User Agent
     *
     * @param request {@link HttpServletRequest}
     * @return user Agent
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(USER_AGENT);
    }

    /**
     * ?????????IE
     *
     * @param request {@link HttpServletRequest}
     * @return ??????IE?????????
     */
    public static boolean isInternetExplorer(HttpServletRequest request) {
        return request.getHeader(USER_AGENT).contains("MSIE");
    }

    /**
     * post??????
     *
     * @param url      ??????
     * @param jsonBody ?????????body??????
     * @return ClientResponse
     */
    public static ClientResponse post(String url, String jsonBody) {
        return post(url, jsonBody, new HashMap<>());
    }

    /**
     * post??????
     *
     * @param url      ??????
     * @param jsonBody ?????????body??????
     * @param headers  ?????????headers
     * @return ClientResponse
     */
    public static ClientResponse post(String url, String jsonBody, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        post.addHeader(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
        addHeaders(headers, post);
        post.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
        return res(post);
    }

    /**
     * ????????????
     *
     * @param url         ??????
     * @param filename    ?????????
     * @param contentBody ???????????????
     * @return ClientResponse
     */
    public static ClientResponse post(String url, String filename, ContentBody contentBody) {
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder reqBuilder = MultipartEntityBuilder.create();
        reqBuilder.addPart(filename, contentBody);
        HttpEntity reqEntity = reqBuilder.build();
        post.setEntity(reqEntity);

        return res(post);
    }

    /**
     * post??????
     *
     * @param url     ??????
     * @param params  ??????
     * @param headers ????????????
     * @return ClientResponse
     */
    public static ClientResponse postForm(String url, Map<String, Object> params, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        // ???????????????????????????
        List<NameValuePair> nvps = new ArrayList<>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            nvps.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        addHeaders(headers, post);
        post.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        return res(post);
    }

    /**
     * ?????? ?????? headers
     *
     * @param headers ??????headers
     * @param post    ??????
     */
    private static void addHeaders(Map<String, String> headers, HttpRequestBase post) {
        if (null != headers && !headers.isEmpty()) {
            Set<Map.Entry<String, String>> headersEntryset = headers.entrySet();
            for (Map.Entry<String, String> header : headersEntryset) {
                post.addHeader(header.getKey(), header.getValue());
            }
        }
    }

    /**
     * ?????????????????????
     *
     * @param response {@link HttpResponse}
     * @return ?????????
     */
    public static int status(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    /**
     * json??????
     *
     * @param response {@link HttpServletResponse}
     * @param object   ????????????
     */
    public static void writeJson(HttpServletResponse response, Object object) {
        writeJson(response, object, DEFAULT_CONTENT_TYPE);
    }

    /**
     * ??????
     *
     * @param response    {@link HttpServletResponse}
     * @param object      ????????????
     * @param contentType ????????????
     */
    public static void writeJson(HttpServletResponse response, Object object, String contentType) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(contentType);
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println(JsonHelper.toJsonString(object));
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ?????????????????????
     *
     * @param request ??????
     * @return Map
     */
    public Map<String, String> headers(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
