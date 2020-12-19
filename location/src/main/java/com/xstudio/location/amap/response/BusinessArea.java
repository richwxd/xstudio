package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

/**
 * 商圈信息
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class BusinessArea {
    /**
     * 商圈所在区域的adcode
     */
    @SerializedName("id")
    private String id;
    /**
     * 商圈中心点经纬度
     */
    @SerializedName("location")
    private String location;
    /**
     * 商圈名称
     */
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
