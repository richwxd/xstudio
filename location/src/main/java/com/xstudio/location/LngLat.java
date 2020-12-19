package com.xstudio.location;

import java.io.Serializable;

/**
 * 坐标
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
@SuppressWarnings("unused")
public class LngLat implements Serializable {
    private static final long serialVersionUID = -863826543934317857L;
    /**
     * 纬度值
     */
    private Double lat;
    /**
     * 经度值
     */
    private Double lng;

    /**
     * @param lng 经度
     * @param lat 纬度
     */
    public LngLat(Double lng, Double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    /**
     * @param lng 经度
     * @param lat 纬度
     */
    public LngLat(String lng, String lat) {
        this.lng = Double.valueOf(lng);
        this.lat = Double.valueOf(lat);
    }

    /**
     * 转成double数组格式
     *
     * @return [经度，纬度]
     */
    public double[] toArray() {
        return new double[]{this.getLng(), this.getLat()};
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
