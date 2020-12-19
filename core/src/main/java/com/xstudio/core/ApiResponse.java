package com.xstudio.core;

import java.io.Serializable;
import java.util.List;

/**
 * 通用消息返回对象
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码，默认0
     */
    private int code = 0;

    /**
     * 返回的数据，可以任意集合或对象
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 结果说明
     */
    private String msg = "";

    /**
     * 结果说明列表
     */
    private List<String> msgs;

    public ApiResponse() {
    }

    public ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = code == 0;
    }

    /**
     * 设置结果
     *
     * @param code 错误码
     * @param msg  消息
     */
    public void setResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = code == 0;
    }

    public boolean getSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
        this.success = code == 0;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        this.success = true;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<String> msgs) {
        this.msgs = msgs;
    }
}
