package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

/**
 * 坐标反解析结果
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class RegeoResponse extends AmapResponse {
    /**
     * regeocode
     */
    @SerializedName("regeocode")
    private Regeocode regeocode;

    public Regeocode getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(Regeocode regeocode) {
        this.regeocode = regeocode;
    }

}
