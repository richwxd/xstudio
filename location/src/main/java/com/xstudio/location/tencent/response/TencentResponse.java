package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class TencentResponse implements Serializable {
    /**
     * message
     */
    @SerializedName("message")
    private String message;
    /**
     * requestId
     */
    @SerializedName("request_id")
    private String requestId;

    /**
     *
     */
    @SerializedName("status")
    private Integer status;

    /**
     * 获取 message.
     *
     * @return message 值
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置 message.
     *
     * <p>通过 getMessage() 获取 message</p>
     *
     * @param message message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取 requestId.
     *
     * @return requestId 值
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * 设置 requestId.
     *
     * <p>通过 getRequestId() 获取 requestId</p>
     *
     * @param requestId requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * 获取 statusX.
     *
     * @return statusX 值
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置 statusX.
     *
     * <p>通过 getStatusX() 获取 statusX</p>
     *
     * @param status statusX
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
