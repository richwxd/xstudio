package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

public class AdInfo {
    /**
     * adcode
     */
    @SerializedName("adcode")
    private String adcode;
    /**
     * city
     */
    @SerializedName("city")
    private String city;
    /**
     * cityCode
     */
    @SerializedName("city_code")
    private String cityCode;
    /**
     * district
     */
    @SerializedName("district")
    private String district;
    /**
     * location
     */
    @SerializedName("location")
    private Location location;
    /**
     * name
     */
    @SerializedName("name")
    private String name;
    /**
     * nation
     */
    @SerializedName("nation")
    private String nation;
    /**
     * nationCode
     */
    /**
     * nation_code : 156
     * adcode : 110108
     * city_code : 156110000
     * name : 中国,北京市,北京市,海淀区
     * location : {"lat":40.045132,"lng":116.375}
     * nation : 中国
     * province : 北京市
     * city : 北京市
     * district : 海淀区
     */

    @SerializedName("nation_code")
    private String nationCode;
    /**
     * province
     */
    @SerializedName("province")
    private String province;

    public String getAdcode() {
        return adcode;
    }

    public String getCity() {
        return city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getNation() {
        return nation;
    }

    public String getNationCode() {
        return nationCode;
    }

    public String getProvince() {
        return province;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
