package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

/**
 * 道路信息
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class Road {
    /**
     * 方位,输入点和此路的相对方位
     */
    @SerializedName("direction")
    private String direction;
    /**
     * 道路到请求坐标的距离,单位：米
     */
    @SerializedName("distance")
    private String distance;
    /**
     * 道路id
     */
    @SerializedName("id")
    private String id;
    /**
     * 坐标点
     */
    @SerializedName("location")
    private String location;
    /**
     * 道路名称
     */
    @SerializedName("name")
    private String name;

    public String getDirection() {
        return direction;
    }

    public String getDistance() {
        return distance;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }
}
