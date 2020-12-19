package com.xstudio.location.tencent.params;


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
     * 经纬度坐标
     */
    @NotEmpty(message = "location不能为空")
    private LngLat location;

    private Boolean getPoi = true;

    private String poiOptions;

    private OutputType output = OutputType.json;

    private String callback;

    /**
     * 获取 location.
     *
     * @return location 值
     */
    public LngLat getLocation() {
        return location;
    }

    /**
     * 设置 location.
     *
     * <p>通过 getLocation() 获取 location</p>
     *
     * @param location location
     */
    public void setLocation(LngLat location) {
        this.location = location;
    }

    /**
     * 获取 getPoi.
     *
     * @return getPoi 值
     */
    public Boolean getGetPoi() {
        return getPoi;
    }

    /**
     * 设置 getPoi.
     *
     * <p>通过 getGetPoi() 获取 getPoi</p>
     *
     * @param getPoi getPoi
     */
    public void setGetPoi(Boolean getPoi) {
        this.getPoi = getPoi;
    }

    /**
     * 获取 poiOptions.
     *
     * @return poiOptions 值
     */
    public String getPoiOptions() {
        return poiOptions;
    }

    /**
     * 设置 poiOptions.
     *
     * <p>通过 getPoiOptions() 获取 poiOptions</p>
     *
     * @param poiOptions poiOptions
     */
    public void setPoiOptions(String poiOptions) {
        this.poiOptions = poiOptions;
    }

    /**
     * 获取 output.
     *
     * @return output 值
     */
    public OutputType getOutput() {
        return output;
    }

    /**
     * 设置 output.
     *
     * <p>通过 getOutput() 获取 output</p>
     *
     * @param output output
     */
    public void setOutput(OutputType output) {
        this.output = output;
    }

    /**
     * 获取 callback.
     *
     * @return callback 值
     */
    public String getCallback() {
        return callback;
    }

    /**
     * 设置 callback.
     *
     * <p>通过 getCallback() 获取 callback</p>
     *
     * @param callback callback
     */
    public void setCallback(String callback) {
        this.callback = callback;
    }
}
