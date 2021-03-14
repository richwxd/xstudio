package io.github.xbeeant.core;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 通用消息返回对象
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
@ApiModel("接口请求返回")
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码，默认0
     */
    @ApiModelProperty(value = "错误码", name = "code", dataType = "int", example = "0", allowableValues = "")
    private int code = 0;

    /**
     * 返回的数据，可以任意集合或对象
     */
    @ApiModelProperty(value = "返回的数据", name = "data", dataType = "any", example = "", allowableValues = "")
    private T data;

    /**
     * 是否成功
     */
    @ApiModelProperty(value = "数据请求状态", name = "success", dataType = "boolean", example = "true", allowableValues = "true|false")
    private boolean success;

    /**
     * 结果说明
     */
    @ApiModelProperty(value = "返回结果说明", name = "msg", dataType = "string", example = "", allowableValues = "")
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
