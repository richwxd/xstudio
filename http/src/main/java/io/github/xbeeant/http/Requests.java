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
     * 链路追中ID
     */
    public static final String TRACE_ID = "TRACE_ID";
    /**
     * 未知IP
     */
    public static final String UNKNOWN = "unknown";
    /**
     * 默认content 类型
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/json";
    /**
     * 设置传输毫秒数
     */
    private static final int SOCKET_TIMEOUT = HttpClientConfig.getHttpSocketTimeout();
    /**
     * 设置每个路由的基础连接数
     */
    private static final int MAX_PRE_ROUTE = HttpClientConfig.getHttpPerRouteSize();
    /**
     * 最大连接数
     */
    private static final int MAX_ROUTE = HttpClientConfig.getHttpMaxPoolSize();
    /**
     * 最大连接
     */
    private static final int MAX_CONNECTION = HttpClientConfig.getHttpMaxPoolSize();
    /**
     * 设置重用连接时间
     */
    private static final int VALIDATE_TIME = 30000;

    private static final String LOCALHOST = "127.0.0.1";

    private static final String USER_AGENT = "User-Agent";

    /**
     * 相当于线程锁,用于线程安全
     */
    private static final Object SYNC_LOCK = new Object();
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(Requests.class);
    /**
     * 监控
     */
    private static final Map<String, ScheduledExecutorService> monitorExecutors = new HashMap<>();
    /**
     * 连接池管理类
     */
    private static final Map<String, PoolingHttpClientConnectionManager> connectionManagers = new HashMap<>();
    /**
     * 发送请求的客户端单例
     */
    private static final Map<String, CloseableHttpClient> HTTP_CLIENTS = new HashMap<>();

    /**
     * 关闭连接池
     */
    public static void closeConnectionPool() {
        try {
            Set<Map.Entry<String, CloseableHttpClient>> clients = HTTP_CLIENTS.entrySet();
            for (Map.Entry<String, CloseableHttpClient> client : clients) {
                client.getValue().close();
            }
            Set<Map.Entry<String, PoolingHttpClientConnectionManager>> managers = connectionManagers.entrySet();
            for (Map.Entry<String, PoolingHttpClientConnectionManager> manager : managers) {
                manager.getValue().close();
            }

            Set<Map.Entry<String, ScheduledExecutorService>> executors = monitorExecutors.entrySet();
            for (Map.Entry<String, ScheduledExecutorService> executor : executors) {
                executor.getValue().shutdown();
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    /**
     * 表单提交代理处理服务
     *
     * @param params 参数
     * @param url    地址
     * @param type   返回结果对象类型
     * @param <T>    返回结果对象类型
     * @return Msg
     */
    public static <T> ApiResponse<T> formPostProxy(Map<String, String> params, String url, Class<T> type) {
        ApiResponse<T> apiResponse = new ApiResponse<>();

        ClientResponse clientResponse = Requests.postForm(url, params);
        if (null == clientResponse) {
            logger.error("API服务调用异常 {} null", url);
            apiResponse.setResult(ErrorCodeConstant.API_INVALID, ErrorCodeConstant.API_INVALID_MSG);
            apiResponse.setMsg("空返回请求");
            return apiResponse;
        }

        if (HttpStatus.SC_OK != clientResponse.getOrigin().getStatusLine().getStatusCode()) {
            logger.error("API服务调用异常 {} {} {}", url, clientResponse.getOrigin().getStatusLine().getStatusCode(), clientResponse.getContent());
            apiResponse.setResult(ErrorCodeConstant.API_INVALID, ErrorCodeConstant.API_INVALID_MSG);
            apiResponse.setMsg(String.valueOf(clientResponse.getOrigin().getStatusLine().getStatusCode()));
            return apiResponse;
        }

        T data = JsonHelper.toObject(clientResponse.getContent(), type);
        if (null == data) {
            logger.debug("API服务调用返回 {} {}", url, clientResponse.getContent());
            apiResponse.setMsg("服务异常[" + apiResponse.getCode() + "]" + apiResponse.getMsg());
            apiResponse.setCode(ErrorCodeConstant.API_INVALID);
            return apiResponse;
        }

        apiResponse.setData(apiResponse.getData());
        return apiResponse;
    }

    /**
     * post请求
     *
     * @param url    地址
     * @param params 参数
     * @return ClientResponse
     */
    public static ClientResponse postForm(String url, Map<String, String> params) {
        HttpPost post = new HttpPost(url);
        //   填入各个表单域的值
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
     * @param method 方法
     * @return {@link ClientResponse}
     */
    private static ClientResponse res(HttpRequestBase method) {
        return res(method, HttpClientConfig.getHttpSocketTimeout());
    }

    /**
     * res
     *
     * @param method 方法
     * @return {@link ClientResponse}
     */
    private static ClientResponse res(HttpRequestBase method, Integer timeout) {
        return res(method, null, timeout);
    }

    /**
     * res
     *
     * @param method 方法
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
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                Charset respCharset = ContentType.getOrDefault(entity).getCharset();
                clientResponse.setContent(EntityUtils.toString(entity, respCharset));
                clientResponse.setOrigin(response);
                EntityUtils.consume(entity);
            }
        } catch (Exception cte) {
            logger.error("{} 接口请求连接超时，由于 {}", method.getURI(), cte.getLocalizedMessage(), cte);
            clientResponse = null;
        } finally {
            method.releaseConnection();
        }
        return clientResponse;
    }

    /**
     * 获取HttpClient对象
     *
     * @param url 地址
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
            // 多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
            synchronized (SYNC_LOCK) {
                client = createHttpClient(MAX_CONNECTION, MAX_PRE_ROUTE, MAX_ROUTE, hostname, port);
                // 开启监控线程,对异常和空闲线程进行关闭
                ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1,
                        new BasicThreadFactory.Builder().namingPattern("http-monitor-schedule-pool-%d").daemon(true).build());
                PoolingHttpClientConnectionManager manager = connectionManagers.get(clientKey);
                executor.scheduleAtFixedRate(new TimerTask() {
                                                 @Override
                                                 public void run() {
                                                     // 关闭异常连接
                                                     manager.closeExpiredConnections();
                                                     // 关闭5s空闲的连接
                                                     manager.closeIdleConnections(HttpClientConfig.getHttpIdleTimeout(), TimeUnit.MILLISECONDS);
                                                     logger.debug("close expired and idle for over {}s connection", HttpClientConfig.getHttpPerRouteSize());
                                                 }
                                             }
                        , HttpClientConfig.getHttpMonitorInterval()
                        , HttpClientConfig.getHttpMonitorInterval()
                        , TimeUnit.MILLISECONDS);
                HTTP_CLIENTS.put(clientKey, client);
                monitorExecutors.put(clientKey, executor);
            }
        }
        return client;
    }

    /**
     * 创建HttpClient对象
     *
     * @param maxTotal    最大请求数
     * @param maxPerRoute 每个路由基础的连接
     * @param maxRoute    最大连接数
     * @param hostname    目标服务器
     * @param port        端口
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
        // 最大连接数
        manager.setMaxTotal(maxTotal);
        // 路由最大连接数
        manager.setDefaultMaxPerRoute(maxPerRoute);
        // 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
        manager.setValidateAfterInactivity(VALIDATE_TIME);
        // 设置socket超时时间
        SocketConfig config = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
        manager.setDefaultSocketConfig(config);


        HttpHost httpHost = new HttpHost(hostname, port);
        // 将目标主机的最大连接数增加
        manager.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);

        connectionManagers.put(clientKey, manager);
        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
            // 如果已经重试了n次，就放弃
            if (executionCount >= HttpClientConfig.getRetryTimes()) {
                // 重试超过n次,放弃请求
                logger.error("retry has more than {} time, give up request", HttpClientConfig.getRetryTimes());
                return false;
            }
            // 如果服务器丢掉了连接，那么就重试
            if (exception instanceof NoHttpResponseException) {
                // 服务器没有响应,可能是服务器断开了连接,应该重试
                logger.error("receive no response from server, retry");
                return true;
            }
            // 不要重试SSL握手异常
            if (exception instanceof SSLHandshakeException) {
                logger.error("SSL handshake failed");
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                // 连接超时
                logger.error("Connection Time out");
                return false;
            }
            // 超时
            if (exception instanceof InterruptedIOException) {
                logger.error("interrupted IO Exception");
                return false;
            }
            // 目标服务器不可达
            if (exception instanceof UnknownHostException) {
                logger.error("server host unknown");
                return false;
            }
            // SSL握手异常
            if (exception instanceof SSLException) {
                logger.error("SSL exception");
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            // 如果请求不是关闭连接的请求
            return !(request instanceof HttpEntityEnclosingRequest);
        };

        return HttpClients.custom()
                .setConnectionManager(manager)
                .setRetryHandler(httpRequestRetryHandler).build();
    }

    /**
     * 发送HTTP_GET请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 远程主机响应正文
     * 1.该方法会自动关闭连接,释放资源
     * 2.方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
     * 3.请求参数含中文时,经测试可直接传入中文,HttpClient会自动编码发给Server,应用时应根据实际效果决
     * 定传入前是否转码
     * 4.该方法会自动获取到响应消息头中[Content-Type:text/html; charset=GBK]的charset值作为响应报文的
     * 解码字符集
     * 5.若响应消息头中无Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1作为响应报文的解码字符
     * 集
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
     * 获取get请求拼接后的值
     *
     * @param params 请求参数
     * @return get请求参数
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
                    logger.error("参数encoding异常", e);
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
     * get请求
     *
     * @param url 请求地址
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
     * 获取响应的body
     *
     * @param response {@link HttpResponse}
     * @return 请求body
     */
    public static String getBody(HttpResponse response) {
        // 获取返回
        HttpEntity httpEntity = response.getEntity();

        // 获取结果
        String body;
        try {
            body = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new HttpResponseFailedException("获取请求返回内容失败", e);
        }
        return body;
    }

    /**
     * 获取请求的Body内容
     *
     * @param request {@link HttpServletRequest}
     * @return body
     * @throws IOException IO异常
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
     * 获取请求服务客户端的IP
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
     * 是否是IE
     *
     * @param request {@link HttpServletRequest}
     * @return 是否IE浏览器
     */
    public static boolean isInternetExplorer(HttpServletRequest request) {
        return request.getHeader(USER_AGENT).contains("MSIE");
    }

    /**
     * post请求
     *
     * @param url      地址
     * @param jsonBody 请求的body数据
     * @return ClientResponse
     */
    public static ClientResponse post(String url, String jsonBody) {
        return post(url, jsonBody, new HashMap<>());
    }

    /**
     * post请求
     *
     * @param url      地址
     * @param jsonBody 请求的body数据
     * @param headers  请求的headers
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
     * 文件上传
     *
     * @param url         地址
     * @param filename    文件名
     * @param contentBody 上传的内容
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
     * post请求
     *
     * @param url     地址
     * @param params  参数
     * @param headers 头部参数
     * @return ClientResponse
     */
    public static ClientResponse postForm(String url, Map<String, Object> params, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        // 填入各个表单域的值
        List<NameValuePair> nvps = new ArrayList<>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            nvps.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        addHeaders(headers, post);
        post.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        return res(post);
    }

    /**
     * 设置 请求 headers
     *
     * @param headers 请求headers
     * @param post    请求
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
     * 获取响应状态码
     *
     * @param response {@link HttpResponse}
     * @return 状态码
     */
    public static int status(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    /**
     * json输出
     *
     * @param response {@link HttpServletResponse}
     * @param object   输出对象
     */
    public static void writeJson(HttpServletResponse response, Object object) {
        writeJson(response, object, DEFAULT_CONTENT_TYPE);
    }

    /**
     * 输出
     *
     * @param response    {@link HttpServletResponse}
     * @param object      输出对象
     * @param contentType 请求类型
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
     * 获取所有请求头
     *
     * @param request 请求
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
