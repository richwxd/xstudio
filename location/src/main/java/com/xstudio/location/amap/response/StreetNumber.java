package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

/**
 * 门牌信息
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class StreetNumber {
    /**
     * 方向
     */
    @SerializedName("direction")
    private String direction;
    /**
     * 门牌地址到请求坐标的距离
     */
    @SerializedName("distance")
    private String distance;
    /**
     * 坐标点
     */
    @SerializedName("location")
    private String location;
    /**
     * 门牌号
     */
    @SerializedName("number")
    private String number;
    /**
     * 街道名称
     */
    @SerializedName("street")
    private String street;

    public String getDirection() {
        return direction;
    }

    public String getDistance() {
        return distance;
    }

    public String getLocation() {
        return location;
    }

    public String getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
