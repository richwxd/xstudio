package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 地址元素
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class AddressComponent {
    /**
     * 行政区编码
     */
    @SerializedName("adcode")
    private String adcode;
    /**
     * 楼信息列表
     */
    @SerializedName("building")
    private Building building;
    /**
     * 商圈信息
     */
    @SerializedName("businessAreas")
    private List<BusinessArea> businessAreas;
    /**
     * city
     */
    @SerializedName("city")
    private List<?> city;
    /**
     * 城市编码
     */
    @SerializedName("citycode")
    private String citycode;
    /**
     * 国家
     */
    @SerializedName("country")
    private String country;
    /**
     * 坐标点所在区
     */
    @SerializedName("district")
    private String district;
    /**
     * 社区信息
     */
    @SerializedName("neighborhood")
    private Neighborhood neighborhood;
    /**
     * 坐标点所在省名称
     */
    @SerializedName("province")
    private String province;
    /**
     * 门牌信息列表
     */
    @SerializedName("streetNumber")
    private StreetNumber streetNumber;
    /**
     * 乡镇街道编码
     */
    @SerializedName("towncode")
    private String towncode;
    /**
     * 坐标点所在乡镇/街道（此街道为社区街道，不是道路信息）
     */
    @SerializedName("township")
    private String township;

    public String getAdcode() {
        return adcode;
    }

    public Building getBuilding() {
        return building;
    }

    public List<BusinessArea> getBusinessAreas() {
        return businessAreas;
    }

    public List<?> getCity() {
        return city;
    }

    public String getCitycode() {
        return citycode;
    }

    public String getCountry() {
        return country;
    }

    public String getDistrict() {
        return district;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public String getProvince() {
        return province;
    }

    public StreetNumber getStreetNumber() {
        return streetNumber;
    }

    public String getTowncode() {
        return towncode;
    }

    public String getTownship() {
        return township;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setBusinessAreas(List<BusinessArea> businessAreas) {
        this.businessAreas = businessAreas;
    }

    public void setCity(List<?> city) {
        this.city = city;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setStreetNumber(StreetNumber streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setTowncode(String towncode) {
        this.towncode = towncode;
    }

    public void setTownship(String township) {
        this.township = township;
    }
}
