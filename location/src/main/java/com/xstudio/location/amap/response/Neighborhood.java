package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

/**
 * 社区信息
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class Neighborhood {
    /**
     * 社区名称
     */
    @SerializedName("name")
    private String name;
    /**
     * POI类型
     */
    @SerializedName("type")
    private String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
