package com.xstudio.location.amap.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Regeocode {
    /**
     * addressComponent
     */
    @SerializedName("addressComponent")
    private AddressComponent addressComponent;
    /**
     * aoi信息列表,请求参数 extensions 为 all 时返回如下内容
     */
    @SerializedName("aois")
    private List<Aoi> aois;
    /**
     * 结构化地址信息
     */
    @SerializedName("formatted_address")
    private String formattedAddress;
    /**
     * poi信息列表
     */
    @SerializedName("pois")
    private List<Poi> pois;
    /**
     * 道路交叉口列表
     */
    @SerializedName("roadinters")
    private List<Roadinter> roadinters;
    /**
     * 道路信息列表
     */
    @SerializedName("roads")
    private List<Road> roads;

    public AddressComponent getAddressComponent() {
        return addressComponent;
    }

    public List<Aoi> getAois() {
        return aois;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public List<Poi> getPois() {
        return pois;
    }

    public List<Roadinter> getRoadinters() {
        return roadinters;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setAddressComponent(AddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public void setAois(List<Aoi> aois) {
        this.aois = aois;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public void setPois(List<Poi> pois) {
        this.pois = pois;
    }

    public void setRoadinters(List<Roadinter> roadinters) {
        this.roadinters = roadinters;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }
}
