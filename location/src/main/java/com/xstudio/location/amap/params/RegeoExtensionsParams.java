package com.xstudio.location.amap.params;

import org.hibernate.validator.constraints.Range;

/**
 * extensions 参数为 all 时才生效
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class RegeoExtensionsParams extends RegeoParams {
    /**
     * homeorcorp 参数的设置可以影响召回 POI 内容的排序策略，目前提供三个可选参数：
     * <p>
     * 0：不对召回的排序策略进行干扰。
     * <p>
     * 1：综合大数据分析将居家相关的 POI 内容优先返回，即优化返回结果中 pois 字段的poi顺序。
     * <p>
     * 2：综合大数据分析将公司相关的 POI 内容优先返回，即优化返回结果中 pois 字段的poi顺序。
     */
    @Range(max = 2)
    private Integer homeorcorp;
    /**
     * 返回附近POI类型
     */
    private String poitype;
    /**
     * 道路等级
     * <p>
     * 可选值：0，1
     * 当roadlevel=0时，显示所有道路
     * 当roadlevel=1时，过滤非主干道路，仅输出主干道路数据
     */
    @Range(max = 1)
    private Integer roadlevel;

    public Integer getHomeorcorp() {
        return homeorcorp;
    }

    public void setHomeorcorp(Integer homeorcorp) {
        this.homeorcorp = homeorcorp;
    }

    public String getPoitype() {
        return poitype;
    }

    public void setPoitype(String poitype) {
        this.poitype = poitype;
    }

    public Integer getRoadlevel() {
        return roadlevel;
    }

    public void setRoadlevel(Integer roadlevel) {
        this.roadlevel = roadlevel;
    }
}
