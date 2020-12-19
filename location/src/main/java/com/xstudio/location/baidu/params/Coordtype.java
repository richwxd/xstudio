package com.xstudio.location.baidu.params;

/**
 * 百度支持的坐标类型
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public enum Coordtype {

    /**
     * 百度经纬度坐标
     */
    bd09ll,
    /**
     * 百度米制坐标
     */
    bd09mc,
    /**
     * 国测局经纬度坐标，仅限中国
     */
    gcj02ll,
    /**
     * GPS经纬度
     */
    wgs84ll;
}
