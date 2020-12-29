package com.xstudio.location.result;

/**
 * 地址
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class AddressComponent {
    /**
     * 行政区编码
     */
    private String adcode;

    /**
     * 城市编码
     */
    private String citycode;
    /**
     * 国家
     */
    private String country;
    /**
     * 坐标点所在区
     */
    private String district;
    /**
     * 坐标点所在省名称
     */
    private String province;
    /**
     * 乡镇街道编码
     */
    private String towncode;
    /**
     * 坐标点所在乡镇/街道（此街道为社区街道，不是道路信息）
     */
    private String township;

    /**
     * 获取 adcode.
     *
     * @return adcode 值
     */
    public String getAdcode() {
        return adcode;
    }

    /**
     * 设置 adcode.
     *
     * <p>通过 getAdcode() 获取 adcode</p>
     *
     * @param adcode adcode
     */
    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    /**
     * 获取 citycode.
     *
     * @return citycode 值
     */
    public String getCitycode() {
        return citycode;
    }

    /**
     * 设置 citycode.
     *
     * <p>通过 getCitycode() 获取 citycode</p>
     *
     * @param citycode citycode
     */
    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    /**
     * 获取 country.
     *
     * @return country 值
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置 country.
     *
     * <p>通过 getCountry() 获取 country</p>
     *
     * @param country country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 获取 district.
     *
     * @return district 值
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置 district.
     *
     * <p>通过 getDistrict() 获取 district</p>
     *
     * @param district district
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 获取 province.
     *
     * @return province 值
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置 province.
     *
     * <p>通过 getProvince() 获取 province</p>
     *
     * @param province province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取 towncode.
     *
     * @return towncode 值
     */
    public String getTowncode() {
        return towncode;
    }

    /**
     * 设置 towncode.
     *
     * <p>通过 getTowncode() 获取 towncode</p>
     *
     * @param towncode towncode
     */
    public void setTowncode(String towncode) {
        this.towncode = towncode;
    }

    /**
     * 获取 township.
     *
     * @return township 值
     */
    public String getTownship() {
        return township;
    }

    /**
     * 设置 township.
     *
     * <p>通过 getTownship() 获取 township</p>
     *
     * @param township township
     */
    public void setTownship(String township) {
        this.township = township;
    }
}
