package io.github.xbeeant.spring.security;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.xbeeant.http.Requests;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 登录参数
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/12
 */
public class LoginParamters implements Serializable {

    /**
     * 其他参数
     */
    @SuppressWarnings("all")
    private Map<String, Object> extras;
    /**
     * 请求参数头
     */
    private Map<String, String> headers;
    /**
     * ip
     */
    private String ip;
    private boolean remember = false;
    /**
     * session
     */
    @SuppressWarnings("all")
    private Map<String, Object> sessions;
    /**
     * userAgent
     */
    private String userAgent;

    public LoginParamters(HttpServletRequest request) {
        String body;
        // body参数
        try {
            body = Requests.getBody(request);
        } catch (IOException e) {
            body = "";
        }

        this.ip = Requests.getIp(request);
        this.userAgent = Requests.getUserAgent(request);
        // get parameters from body
        if (!StringUtils.isEmpty(body) && (body.startsWith("{") || body.startsWith("["))) {
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> parse = new Gson().fromJson(body, type);
            if (null != parse) {
                Set<Map.Entry<String, Object>> entries = parse.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    addExtra(entry.getKey(), entry.getValue());
                }
            }
        }
        // request parameter 参数
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            addExtra(s, request.getParameter(s));
        }
        // header 参数
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            addHeader(key, value);
        }
        // session参数
        Enumeration<String> sessionsNames = request.getSession().getAttributeNames();
        while (sessionsNames.hasMoreElements()) {
            String s = sessionsNames.nextElement();
            addSession(s, request.getSession().getAttribute(s));
        }
    }

    /**
     * 添加参数
     *
     * @param key   key
     * @param value 值
     */
    public void addExtra(String key, Object value) {
        if (null == this.extras) {
            this.extras = new HashMap<>();
        }
        this.extras.put(key, value);
    }

    /**
     * 添加请求头
     *
     * @param key   key
     * @param value 值
     */
    public void addHeader(String key, String value) {
        if (null == this.headers) {
            this.headers = new HashMap<>();
        }
        this.headers.put(key, value);
    }

    public void addSession(String key, Object value) {
        if (null == this.sessions) {
            this.sessions = new HashMap<>();
        }
        this.sessions.put(key, value);
    }

    /**
     * 获取属性
     *
     * @param key 属性键
     * @return 属性值
     */
    public Object get(String key) {
        if (null == this.extras) {
            return null;
        }

        return this.extras.get(key);
    }

    /**
     * get field 其他参数
     *
     * @return extras 其他参数
     */
    public Map<String, Object> getExtras() {
        return this.extras;
    }

    /**
     * set field 其他参数
     *
     * @param extras 其他参数
     */
    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    /**
     * get field 请求参数头
     *
     * @return headers 请求参数头
     */
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    /**
     * set field 请求参数头
     *
     * @param headers 请求参数头
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * get field ip
     *
     * @return ip ip
     */
    public String getIp() {
        return this.ip;
    }

    /**
     * set field ip
     *
     * @param ip ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * get field session
     *
     * @return sessions session
     */
    public Map<String, Object> getSessions() {
        return this.sessions;
    }

    /**
     * set field session
     *
     * @param sessions session 会话信息
     */
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }

    /**
     * get field userAgent
     *
     * @return userAgent userAgent
     */
    public String getUserAgent() {
        return this.userAgent;
    }

    /**
     * set field userAgent
     *
     * @param userAgent userAgent user-agent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * get field
     *
     * @return remember
     */
    public boolean isRemember() {
        return this.remember;
    }

    /**
     * set field
     *
     * @param remember 记住登录
     */
    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
