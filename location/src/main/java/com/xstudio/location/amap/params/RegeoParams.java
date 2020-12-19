package com.xstudio.location.amap.params;


import com.xstudio.location.LngLat;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

public class RegeoParams {
    public RegeoParams() {
    }

    public RegeoParams(@NotEmpty(message = "location不能为空") LngLat location) {
        this.location = location;
    }

    /**
     * 批量查询控制
     */
    private Boolean batch = false;
    /**
     * 回调函数
     */
    private String callback;
    /**
     * extensions 参数默认取值是 base，也就是返回基本地址信息；
     * <p>
     * extensions 参数取值为 all 时会返回基本地址信息、附近 POI 内容、道路信息以及道路交叉口信息。
     */
    private ExtensionType extensions = ExtensionType.base;

    /**
     * 经纬度坐标
     */
    @NotEmpty(message = "location不能为空")
    private LngLat location;
    /**
     * 返回数据格式类型。
     */
    private OutputType output = OutputType.JSON;
    /**
     * 搜索半径
     */
    @Range(max = 3000)
    private Integer radius = 1000;
    /**
     * 数字签名
     */
    private String sig;


    /**
     * 获取
     *
     * @return batch 批量查询控制
     */
    public Boolean getBatch() {
        return this.batch;
    }

    /**
     * 设置
     *
     * @param batch 批量查询控制
     */
    public void setBatch(Boolean batch) {
        this.batch = batch;
    }

    /**
     * 获取回调函数
     *
     * @return callback 回调函数
     */
    public String getCallback() {
        return this.callback;
    }

    /**
     * 设置回调函数
     *
     * @param callback 回调函数
     */
    public void setCallback(String callback) {
        this.callback = callback;
    }

    /**
     * 获取extensions 参数默认取值是 base，也就是返回基本地址信息；
     * <p>
     * extensions 参数取值为 all 时会返回基本地址信息、附近 POI 内容、道路信息以及道路交叉口信息。
     *
     * @return extensions 返回结果控制
     */
    public ExtensionType getExtensions() {
        return this.extensions;
    }

    /**
     * 设置extensions 参数默认取值是 base，也就是返回基本地址信息；
     * <p>
     * extensions 参数取值为 all 时会返回基本地址信息、附近 POI 内容、道路信息以及道路交叉口信息。
     *
     * @param extensions extensions 返回结果控制
     */
    public void setExtensions(ExtensionType extensions) {
        this.extensions = extensions;
    }

    /**
     * 获取经纬度坐标
     *
     * @return location 经纬度坐标
     */
    public LngLat getLocation() {
        return this.location;
    }

    /**
     * 设置经纬度坐标
     *
     * @param location 经纬度坐标
     */
    public void setLocation(LngLat location) {
        this.location = location;
    }

    /**
     * 获取返回数据格式类型。
     *
     * @return output 返回数据格式类型。
     */
    public OutputType getOutput() {
        return this.output;
    }

    /**
     * 设置返回数据格式类型。
     *
     * @param output 返回数据格式类型。
     */
    public void setOutput(OutputType output) {
        this.output = output;
    }

    /**
     * 获取搜索半径
     *
     * @return radius 搜索半径
     */
    public Integer getRadius() {
        return this.radius;
    }

    /**
     * 设置搜索半径
     *
     * @param radius 搜索半径
     */
    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    /**
     * 获取数字签名
     *
     * @return sig 数字签名
     */
    public String getSig() {
        return this.sig;
    }

    /**
     * 设置数字签名
     *
     * @param sig 数字签名
     */
    public void setSig(String sig) {
        this.sig = sig;
    }
}
