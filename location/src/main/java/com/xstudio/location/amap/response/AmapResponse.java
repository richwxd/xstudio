package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 高德地图统一服务
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class AmapResponse implements Serializable {
    private static final long serialVersionUID = 6934671893492013222L;
    /**
     * 当 status 为 0 时，info 会返回具体错误原因，否则返回“OK”。详情可以参考
     */
    @SerializedName("info")
    private String info;
    /**
     * infocode
     */
    @SerializedName("infocode")
    private Integer infocode;

    /**
     * 返回结果状态值
     * <p>
     * 返回值为 0 或 1，0 表示请求失败；1 表示请求成功。
     */
    @SerializedName("status")
    private String status;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getInfocode() {
        return infocode;
    }

    public void setInfocode(Integer infocode) {
        this.infocode = infocode;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
