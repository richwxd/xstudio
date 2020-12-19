package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

public class AddressComponent {
    /**
     * city
     */
    @SerializedName("city")
    private String city;
    /**
     * district
     */
    @SerializedName("district")
    private String district;
    /**
     * nation
     */
    /**
     * nation : 中国
     * province : 北京市
     * city : 北京市
     * district : 海淀区
     * street : 北四环西路
     * street_number : 北四环西路66号
     */

    @SerializedName("nation")
    private String nation;
    /**
     * province
     */
    @SerializedName("province")
    private String province;
    /**
     * street
     */
    @SerializedName("street")
    private String street;
    /**
     * streetNumber
     */
    @SerializedName("street_number")
    private String streetNumber;

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getNation() {
        return nation;
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

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setNation(String nation) {
        this.nation = nation;
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
}
