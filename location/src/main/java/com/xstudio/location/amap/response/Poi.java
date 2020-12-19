package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * poi信息
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class Poi {
    /**
     * poi地址信息
     */
    @SerializedName("address")
    private String address;
    /**
     * poi所在商圈名称
     */
    @SerializedName("businessarea")
    private String businessarea;
    /**
     * 方向,为输入点相对建筑物的方位
     */
    @SerializedName("direction")
    private String direction;
    /**
     * 该POI的中心点到请求坐标的距离,单位：米
     */
    @SerializedName("distance")
    private String distance;

    /**
     * poi的id
     */
    @SerializedName("id")
    private String id;
    /**
     * 坐标点
     */
    @SerializedName("location")
    private String location;
    /**
     * poi点名称
     */
    @SerializedName("name")
    private String name;
    /**
     * poi点权重
     */
    @SerializedName("poiweight")
    private String poiweight;
    /**
     * 电话
     */
    @SerializedName("tel")
    private List<String> tel;
    /**
     * poi类型
     */
    @SerializedName("type")
    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessarea() {
        return businessarea;
    }

    public void setBusinessarea(String businessarea) {
        this.businessarea = businessarea;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

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

    public String getPoiweight() {
        return poiweight;
    }

    public void setPoiweight(String poiweight) {
        this.poiweight = poiweight;
    }

    public List<String> getTel() {
        return tel;
    }

    public void setTel(List<String> tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
