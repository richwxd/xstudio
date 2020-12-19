package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

/**
 * aoi信息
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class Aoi {
    /**
     * 所属 aoi 所在区域编码
     */
    @SerializedName("adcode")
    private String adcode;
    /**
     * 所属aoi点面积，单位：平方米
     */
    @SerializedName("area")
    private String area;
    /**
     * 输入经纬度是否在aoi面之中
     * <p>
     * 0，代表在aoi内
     * <p>
     * 其余整数代表距离AOI的距离
     */
    @SerializedName("distance")
    private String distance;
    /**
     * 所属 aoi的id
     */
    @SerializedName("id")
    private String id;
    /**
     * 所属 aoi 中心点坐标
     */
    @SerializedName("location")
    private String location;
    /**
     * 所属 aoi 名称
     */
    @SerializedName("name")
    private String name;
    /**
     * type
     */
    @SerializedName("type")
    private String type;

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
