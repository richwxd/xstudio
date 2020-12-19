package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

/**
 * 道路交叉口
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class Roadinter {
    /**
     * 方位,输入点相对路口的方位
     */
    @SerializedName("direction")
    private String direction;
    /**
     * 交叉路口到请求坐标的距离,单位：米
     */
    @SerializedName("distance")
    private String distance;
    /**
     * 第一条道路id
     */
    @SerializedName("first_id")
    private String firstId;
    /**
     * 第一条道路名称
     */
    @SerializedName("first_name")
    private String firstName;
    /**
     * 路口经纬度
     */
    @SerializedName("location")
    private String location;
    /**
     * 第二条道路id
     */
    @SerializedName("second_id")
    private String secondId;
    /**
     * 第二条道路名称
     */
    @SerializedName("second_name")
    private String secondName;

    public String getDirection() {
        return direction;
    }

    public String getDistance() {
        return distance;
    }

    public String getFirstId() {
        return firstId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLocation() {
        return location;
    }

    public String getSecondId() {
        return secondId;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
