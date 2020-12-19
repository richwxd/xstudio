package com.xstudio.location.baidu.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegeoResult {
    /**
     * addressComponent
     */
    @SerializedName("addressComponent")
    private AddressComponent addressComponent;
    /**
     * business
     */
    @SerializedName("business")
    private String business;
    /**
     * cityCode
     */
    @SerializedName("cityCode")
    private Integer cityCode;
    /**
     * formattedAddress
     */
    @SerializedName("formatted_address")
    private String formattedAddress;
    /**
     * location
     */
    @SerializedName("location")
    private Location location;
    /**
     * poiRegions
     */
    @SerializedName("poiRegions")
    private List<?> poiRegions;
    /**
     * pois
     */
    @SerializedName("pois")
    private List<?> pois;
    /**
     * roads
     */
    @SerializedName("roads")
    private List<?> roads;
    /**
     * sematicDescription
     */
    @SerializedName("sematic_description")
    private String sematicDescription;

    public AddressComponent getAddressComponent() {
        return addressComponent;
    }

    public String getBusiness() {
        return business;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public Location getLocation() {
        return location;
    }

    public List<?> getPoiRegions() {
        return poiRegions;
    }

    public List<?> getPois() {
        return pois;
    }

    public List<?> getRoads() {
        return roads;
    }

    public String getSematicDescription() {
        return sematicDescription;
    }

    public void setAddressComponent(AddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPoiRegions(List<?> poiRegions) {
        this.poiRegions = poiRegions;
    }

    public void setPois(List<?> pois) {
        this.pois = pois;
    }

    public void setRoads(List<?> roads) {
        this.roads = roads;
    }

    public void setSematicDescription(String sematicDescription) {
        this.sematicDescription = sematicDescription;
    }
}
