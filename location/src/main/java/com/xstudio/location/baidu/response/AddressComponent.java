package com.xstudio.location.baidu.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class AddressComponent {
    /**
     * 行政区划代码
     */
    @SerializedName("adcode")
    private String adcode;
    /**
     * 城市名
     */
    @SerializedName("city")
    private String city;
    /**
     * 城市所在级别（仅国外有参考意义。国外行政区划与中国有差异，城市对应的层级不一定为『city』。country、province、city、district、town分别对应0-4级，若city_level=3，则district层级为该国家的city层级）
     */
    @SerializedName("city_level")
    private Integer cityLevel;
    /**
     * 国家
     */
    @SerializedName("country")
    private String country;
    /**
     * 国家编码
     */
    @SerializedName("country_code")
    private Integer countryCode;
    /**
     * 国家英文缩写（三位）
     */
    @SerializedName("country_code_iso")
    private String countryCodeIso;
    /**
     * 国家英文缩写（两位）
     */
    @SerializedName("country_code_iso2")
    private String countryCodeIso2;
    /**
     * 相对当前坐标点的方向，当有门牌号的时候返回数据
     */
    @SerializedName("direction")
    private String direction;
    /**
     * 相对当前坐标点的距离，当有门牌号的时候返回数据
     */
    @SerializedName("distance")
    private String distance;
    /**
     * 区县名
     */
    @SerializedName("district")
    private String district;
    /**
     * 省名
     */
    @SerializedName("province")
    private String province;
    /**
     * 街道名（行政区划中的街道层级）
     */
    @SerializedName("street")
    private String street;
    /**
     * 街道门牌号
     */
    @SerializedName("street_number")
    private String streetNumber;
    /**
     * 乡镇名
     */
    @SerializedName("town")
    private String town;
    /**
     * 乡镇id
     */
    @SerializedName("town_code")
    private String towncode;

    public String getAdcode() {
        return adcode;
    }

    public String getCity() {
        return city;
    }

    public Integer getCityLevel() {
        return cityLevel;
    }

    public String getCountry() {
        return country;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public String getCountryCodeIso() {
        return countryCodeIso;
    }

    public String getCountryCodeIso2() {
        return countryCodeIso2;
    }

    public String getDirection() {
        return direction;
    }

    public String getDistance() {
        return distance;
    }

    public String getDistrict() {
        return district;
    }

    public String getProvince() {
        return province;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getTown() {
        return town;
    }

    public String getTowncode() {
        return towncode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityLevel(Integer cityLevel) {
        this.cityLevel = cityLevel;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public void setCountryCodeIso(String countryCodeIso) {
        this.countryCodeIso = countryCodeIso;
    }

    public void setCountryCodeIso2(String countryCodeIso2) {
        this.countryCodeIso2 = countryCodeIso2;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setTowncode(String towncode) {
        this.towncode = towncode;
    }
}
