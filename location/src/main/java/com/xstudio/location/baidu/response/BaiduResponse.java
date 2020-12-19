package com.xstudio.location.baidu.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaiduResponse implements Serializable {
    private static final long serialVersionUID = -5951447400926299857L;
    /**
     * 返回结果状态值， 成功返回0，其他值请查看下方返回码状态表。
     */
    @SerializedName("status")
    private Integer status;

    /**
     * 获取 status.
     *
     * @return status 值
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置 status.
     *
     * <p>通过 getStatus() 获取 status</p>
     *
     * @param status status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
