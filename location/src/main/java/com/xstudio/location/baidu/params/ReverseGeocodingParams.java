package com.xstudio.location.baidu.params;

import com.xstudio.location.LngLat;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

public class ReverseGeocodingParams implements Serializable {
    private String callback;
    private Coordtype coordtype = Coordtype.bd09ll;
    /**
     * extensions_poi=0，不召回pois数据。
     * extensions_poi=1，返回pois数据，默认显示周边1000米内的poi
     */
    private Boolean extensionsPoi;
    /**
     * 当取值为true时，召回坐标周围最近的3条道路数据。区别于行政区划中的street参数（street参数为行政区划中的街道，和普通道路不对应）
     */
    private Boolean extensionsRoad;
    /**
     * 当取值为true时，行政区划返回乡镇级数据（仅国内召回乡镇数据）。默认不访问。
     */
    private Boolean extensionsTown = false;
    /**
     * 指定召回的行政区划语言类型。
     * 召回行政区划语言list（全量支持的语言见示例）。
     * 当language=local时，根据请求中坐标所对应国家的母语类型，自动选择对应语言类型的行政区划召回。
     * 目前支持多语言的行政区划区划包含country、province、city、district
     */
    private String language;
    /**
     * 当用户指定language参数时，是否自动填充行政区划。
     * 1填充，0不填充。
     * 填充：当服务按某种语言类别召回时，若某一行政区划层级的语言数据未覆盖，则按照“英文→中文→本地语言”类别行政区划数据对该层级行政区划进行填充，保证行政区划数据召回完整性。
     */
    private String languageAuto;
    /**
     * 根据经纬度坐标获取地址。
     */
    private LngLat location;
    private OutputType output = OutputType.json;
    private String poiTypes;
    @Range(max = 1000)
    private Integer radius = 1000;
    /**
     * 可选参数，添加后返回国测局经纬度坐标或百度米制坐标
     */
    private Coordtype retCoordtype;

    public ReverseGeocodingParams() {
    }

    public ReverseGeocodingParams(LngLat lngLat) {
        this.location = lngLat;
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

    /**
     * 获取 coordtype.
     *
     * @return coordtype 值
     */
    public Coordtype getCoordtype() {
        return coordtype;
    }

    /**
     * 设置 coordtype.
     *
     * <p>通过 getCoordtype() 获取 coordtype</p>
     *
     * @param coordtype coordtype
     */
    public void setCoordtype(Coordtype coordtype) {
        this.coordtype = coordtype;
    }

    /**
     * 获取 extensionsPoi.
     *
     * @return extensionsPoi 值
     */
    public Boolean getExtensionsPoi() {
        return extensionsPoi;
    }

    /**
     * 设置 extensionsPoi.
     *
     * <p>通过 getExtensionsPoi() 获取 extensionsPoi</p>
     *
     * @param extensionsPoi extensionsPoi
     */
    public void setExtensionsPoi(Boolean extensionsPoi) {
        this.extensionsPoi = extensionsPoi;
    }

    /**
     * 获取 extensionsRoad.
     *
     * @return extensionsRoad 值
     */
    public Boolean getExtensionsRoad() {
        return extensionsRoad;
    }

    /**
     * 设置 extensionsRoad.
     *
     * <p>通过 getExtensionsRoad() 获取 extensionsRoad</p>
     *
     * @param extensionsRoad extensionsRoad
     */
    public void setExtensionsRoad(Boolean extensionsRoad) {
        this.extensionsRoad = extensionsRoad;
    }

    /**
     * 获取 extensionsTown.
     *
     * @return extensionsTown 值
     */
    public Boolean getExtensionsTown() {
        return extensionsTown;
    }

    /**
     * 设置 extensionsTown.
     *
     * <p>通过 getExtensionsTown() 获取 extensionsTown</p>
     *
     * @param extensionsTown extensionsTown
     */
    public void setExtensionsTown(Boolean extensionsTown) {
        this.extensionsTown = extensionsTown;
    }

    /**
     * 获取 language.
     *
     * @return language 值
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 设置 language.
     *
     * <p>通过 getLanguage() 获取 language</p>
     *
     * @param language language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 获取 languageAuto.
     *
     * @return languageAuto 值
     */
    public String getLanguageAuto() {
        return languageAuto;
    }

    /**
     * 设置 languageAuto.
     *
     * <p>通过 getLanguageAuto() 获取 languageAuto</p>
     *
     * @param languageAuto languageAuto
     */
    public void setLanguageAuto(String languageAuto) {
        this.languageAuto = languageAuto;
    }

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
     * 获取 poiTypes.
     *
     * @return poiTypes 值
     */
    public String getPoiTypes() {
        return poiTypes;
    }

    /**
     * 设置 poiTypes.
     *
     * <p>通过 getPoiTypes() 获取 poiTypes</p>
     *
     * @param poiTypes poiTypes
     */
    public void setPoiTypes(String poiTypes) {
        this.poiTypes = poiTypes;
    }

    /**
     * 获取 radius.
     *
     * @return radius 值
     */
    public Integer getRadius() {
        return radius;
    }

    /**
     * 设置 radius.
     *
     * <p>通过 getRadius() 获取 radius</p>
     *
     * @param radius radius
     */
    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    /**
     * 获取 retCoordtype.
     *
     * @return retCoordtype 值
     */
    public Coordtype getRetCoordtype() {
        return retCoordtype;
    }

    /**
     * 设置 retCoordtype.
     *
     * <p>通过 getRetCoordtype() 获取 retCoordtype</p>
     *
     * @param retCoordtype retCoordtype
     */
    public void setRetCoordtype(Coordtype retCoordtype) {
        this.retCoordtype = retCoordtype;
    }
}
