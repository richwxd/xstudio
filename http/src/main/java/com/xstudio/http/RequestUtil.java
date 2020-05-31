package com.xstudio.http;

import com.alibaba.fastjson.JSON;
import com.xstudio.core.ErrorConstant;
import com.xstudio.core.Msg;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
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
import org.apache.http.conn.ssl.NoopHostnameVerifier;
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

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaobiao
 * @version 2020/2/12
 */
public class RequestUtil {
    /**
     * 默认content 类型
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/json";
    /**
     * form表单提交类型
     */
    private static final String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

    /**
     * 设置传输毫秒数
     */
    private static final int SOCKET_TIMEOUT = HttpClientConfig.httpSocketTimeout;

    /**
     * 设置每个路由的基础连接数
     */
    private static final int MAX_PRE_ROUTE = HttpClientConfig.httpMaxPoolSize;
    /**
     * 最大连接数
     */
    private static final int MAX_ROUTE = HttpClientConfig.httpMaxPoolSize;
    /**
     * 最大连接
     */
    private static final int MAX_CONNECTION = HttpClientConfig.httpMaxPoolSize;

    /**
     * 设置重用连接时间
     */
    private static final int VALIDATE_TIME = 30000;

    private static final String LOCALHOST = "127.0.0.1";

    private static final String USER_AGENT = "User-Agent";

    private final static Object SYNC_LOCK = new Object();

    /**
     * 监控
     */
    private static ScheduledExecutorService monitorExecutor;

    /**
     * 连接池管理类
     */
    private static PoolingHttpClientConnectionManager connectionManager;

    /**
     * 发送请求的客户端单例
     */
    private static CloseableHttpClient httpClient = null;

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

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
        if (httpClient == null) {
            // 多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
            synchronized (SYNC_LOCK) {
                if (httpClient == null) {
                    httpClient = createHttpClient(MAX_CONNECTION, MAX_PRE_ROUTE, MAX_ROUTE, hostname, port);
                    // 开启监控线程,对异常和空闲线程进行关闭
                    monitorExecutor = new ScheduledThreadPoolExecutor(1,
                            new BasicThreadFactory.Builder().namingPattern("http-monitor-schedule-pool-%d").daemon(true).build());
                    monitorExecutor.scheduleAtFixedRate(new TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                // 关闭异常连接
                                                                connectionManager.closeExpiredConnections();
                                                                // 关闭5s空闲的连接
                                                                connectionManager.closeIdleConnections(HttpClientConfig.httpIdleTimeout, TimeUnit.MILLISECONDS);
                                                                logger.debug("close expired and idle for over {}s connection", HttpClientConfig.httpIdleTimeout);
                                                            }
                                                        }
                            , HttpClientConfig.httpMonitorInterval
                            , HttpClientConfig.httpMonitorInterval
                            , TimeUnit.MILLISECONDS);
                }
            }
        }
        return httpClient;
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

        connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 最大连接数
        connectionManager.setMaxTotal(maxTotal);
        // 路由最大连接数
        connectionManager.setDefaultMaxPerRoute(maxPerRoute);
        // 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
        connectionManager.setValidateAfterInactivity(VALIDATE_TIME);
        // 设置socket超时时间
        SocketConfig config = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
        connectionManager.setDefaultSocketConfig(config);

        HttpHost httpHost = new HttpHost(hostname, port);
        // 将目标主机的最大连接数增加
        connectionManager.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
            // 如果已经重试了n次，就放弃
            if (executionCount >= HttpClientConfig.retryTimes) {
                // 重试超过n次,放弃请求
                logger.error("retry has more than {} time, give up request", HttpClientConfig.retryTimes);
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
                return false;
            }
            // 超时
            if (exception instanceof InterruptedIOException) {
                return false;
            }
            // 目标服务器不可达
            if (exception instanceof UnknownHostException) {
                return false;
            }
            // 连接被拒绝
            if (exception instanceof ConnectTimeoutException) {
                // 连接超时
                return false;
            }
            // SSL握手异常
            if (exception instanceof SSLException) {
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                // 如果请求不是关闭连接的请求
                return true;
            }
            return false;
        };

        logger.info("{} {} 客户池状态：{}", hostname, port, connectionManager.getTotalStats());

        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setRetryHandler(httpRequestRetryHandler).build();
    }

    /**
     * SSL的socket工厂创建
     *
     * @return {@link SSLConnectionSocketFactory}
     */
    private static SSLConnectionSocketFactory createSslConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        SSLContext context;
        try {
            // 创建TrustManager() 用于解决javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
            X509TrustManager x509m = new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }
            };
            context = SSLContext.getInstance(SSLConnectionSocketFactory.SSL);
            // 初始化SSLContext实例
            try {
                //最关键的必须有这一步，否则抛出SSLContextImpl未被初始化的异常
                context.init(null,
                        new TrustManager[]{x509m},
                        new java.security.SecureRandom());
            } catch (KeyManagementException e) {
                logger.error("SSL上下文初始化失败， 由于 {}", e.getLocalizedMessage());
            }
            // 创建SSLSocketFactory
            // 不校验域名 ,取代以前验证规则
            sslsf = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            logger.error("SSL上下文创建失败，由于 {}", e.getLocalizedMessage());
        }
        return sslsf;
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
     * 获取响应状态码
     *
     * @param response {@link HttpResponse}
     * @return 状态码
     */
    public static int getStatus(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
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
        String body = "";
        try {
            body = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (IOException e) {
            logger.error("获取请求返回返回失败", e);
        }
        return body;
    }

    /**
     * 获取请求服务客户端的IP
     *
     * @param request {@link HttpServletRequest}
     * @return IP
     */
    public static String getIp(HttpServletRequest request) {
        String serverIp = "unknown";
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
            return "unknown";
        }
        return serverIp;
    }

    /**
     * get User Agent
     *
     * @param request {@link HttpServletRequest}
     * @return user Agent
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = "";
        try {
            userAgent = request.getHeader(USER_AGENT);
        } catch (Exception e) {
            logger.trace("header获取失败", e);
        }
        return userAgent;
    }

    /**
     * get server name
     *
     * @param request {@link HttpServletRequest}
     * @return String
     */
    public static String getServerName(HttpServletRequest request) {

        String serverName = "";
        try {
            serverName = request.getHeader(USER_AGENT);
        } catch (Exception e) {
            return "unknown";
        }
        return serverName;
    }

    /**
     * @param request {@link HttpServletRequest}
     * @return Integer
     */
    public static int getServerPort(HttpServletRequest request) {
        int port = 0;
        try {
            port = request.getServerPort();
        } catch (Exception e) {
            logger.trace("", e);
        }
        return port;
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
     * json输出
     *
     * @param response {@link HttpServletResponse}
     * @param object   输出对象
     */
    public static void writeJson(HttpServletResponse response, Object object) {
        write(response, object, DEFAULT_CONTENT_TYPE);
    }

    /**
     * 输出
     *
     * @param response    {@link HttpServletResponse}
     * @param object      输出对象
     * @param contentType 请求类型
     */
    public static void write(HttpServletResponse response, Object object, String contentType) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(contentType);
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println(JSON.toJSONString(object));
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 发送HTTP_GET请求
     *
     * @param url   请求地址
     * @param param 含参数
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
    public static ClientResponse get(String url, String param) {
        if (null != param) {
            url += "?" + param;
        }
        // 响应内容
        HttpGet httpget = new HttpGet(url);
        return res(httpget);
    }

    private static ClientResponse res(HttpRequestBase method) {
        HttpClientContext context = HttpClientContext.create();
        ClientResponse clientResponse = new ClientResponse();
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
            logger.error("请求连接超时，由于 {}", cte.getLocalizedMessage(), cte);
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        return clientResponse;
    }

    /**
     * get请求
     *
     * @param url 请求地址
     * @return ClientResponse
     */
    public static ClientResponse get(String url) {
        HttpGet get = new HttpGet(url);
        return res(get);
    }

    /**
     * get请求
     *
     * @param url    请求地址
     * @param cookie Cookie内容
     * @return ClientResponse
     */
    public static ClientResponse getByCookie(String url, String cookie) {
        HttpGet get = new HttpGet(url);
        if (StringUtils.isNotBlank(cookie)) {
            get.addHeader("cookie", cookie);
        }

        return res(get);
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
        try {
            post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("请求参数设置失败", e);
        }
        return res(post);
    }

    /**
     * 设置 请求 headers
     *
     * @param headers 请求headers
     * @param post    请求
     */
    private static void setHeaders(Map<String, String> headers, HttpPost post) {
        if (null != headers && !headers.isEmpty()) {
            Set<Map.Entry<String, String>> headersEntryset = headers.entrySet();
            for (Map.Entry<String, String> header : headersEntryset) {
                post.addHeader(header.getKey(), header.getValue());
            }
        }
    }


    /**
     * post请求
     *
     * @param url     地址
     * @param data    请求的body数据
     * @param headers 请求的headers
     * @return ClientResponse
     */
    public static ClientResponse post(String url, String data, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        if (StringUtils.isNotBlank(data) && !headers.containsKey("Content-Type")) {
            post.addHeader("Content-Type", DEFAULT_CONTENT_TYPE);
        }
        setHeaders(headers, post);
        post.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
        return res(post);
    }

    /**
     * post请求
     *
     * @param url  地址
     * @param data 请求的body数据
     * @return ClientResponse
     */
    public static ClientResponse post(String url, String data) {
        HttpPost post = new HttpPost(url);
        if (StringUtils.isNotBlank(data)) {
            post.addHeader("Content-Type", DEFAULT_CONTENT_TYPE);
        }
        post.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
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
     * 获取get请求拼接后的值
     *
     * @param params 请求参数
     * @return get请求参数
     * @throws UnsupportedEncodingException {@link UnsupportedEncodingException}
     */
    public static String getCanonicalQueryString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder queryString = new StringBuilder("");
        Set<Map.Entry<String, String>> entries = params.entrySet();
        String value;
        for (Map.Entry<String, String> entry : entries) {
            try {
                value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                throw new UnsupportedEncodingException(e.getMessage());
            }
            queryString.append("&").append(entry.getKey()).append("=").append(value);
        }

        return queryString.toString().substring(1);
    }

    /**
     * 表单提交代理处理服务
     *
     * @param params 参数
     * @param url    地址
     * @param type   返回结果对象类型
     * @param <T>    返回结果对象类型
     * @return {@link Msg}
     */
    public static <T> Msg<T> formPostProxy(Map<String, String> params, String url, Type type) {
        Msg<T> msg = new Msg<>();

        ClientResponse clientResponse = RequestUtil.postForm(url, params);

        if (HttpStatus.SC_OK != clientResponse.getOrigin().getStatusLine().getStatusCode()) {
            logger.error("API服务调用异常 {} {}", clientResponse.getOrigin().getStatusLine().getStatusCode(), clientResponse.getContent());
            msg.setResult(ErrorConstant.API_INVALID, ErrorConstant.API_INVALID_MSG);
            msg.setMsg(String.valueOf(clientResponse.getOrigin().getStatusLine().getStatusCode()));
            return msg;
        }

        msg = JSON.parseObject(clientResponse.getContent(), type);
        if (0 != msg.getCode()) {
            logger.debug("API服务调用返回 {}", clientResponse.getContent());
            msg.setMsg("服务异常[" + msg.getCode() + "]" + msg.getMsg());
            msg.setCode(ErrorConstant.API_INVALID);
            return msg;
        }

        msg.setData(msg.getData());
        return msg;
    }

    /**
     * 关闭连接池
     */
    public static void closeConnectionPool() {
        try {
            httpClient.close();
            connectionManager.close();
            monitorExecutor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有请求头
     *
     * @param request 请求
     * @return Map
     */
    public Map<String, String> getHeaders(HttpServletRequest request) {
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
