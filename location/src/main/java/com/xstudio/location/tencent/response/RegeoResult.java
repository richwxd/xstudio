package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegeoResult {
    /**
     * adInfo
     */
    @SerializedName("ad_info")
    private AdInfo adInfo;
    /**
     * address
     */
    @SerializedName("address")
    private String address;
    /**
     * addressComponent
     */
    @SerializedName("address_component")
    private AddressComponent addressComponent;
    /**
     * addressReference
     */
    @SerializedName("address_reference")
    private AddressReference addressReference;
    /**
     * formattedAddresses
     */
    @SerializedName("formatted_addresses")
    private FormattedAddresses formattedAddresses;
    /**
     * location
     */
    @SerializedName("location")
    private Location location;
    /**
     * poiCount
     */
    @SerializedName("poi_count")
    private Integer poiCount;
    /**
     * pois
     */
    @SerializedName("pois")
    private List<Pois> pois;

    public AdInfo getAdInfo() {
        return adInfo;
    }

    public String getAddress() {
        return address;
    }

    public AddressComponent getAddressComponent() {
        return addressComponent;
    }

    public AddressReference getAddressReference() {
        return addressReference;
    }

    public FormattedAddresses getFormattedAddresses() {
        return formattedAddresses;
    }

    public Location getLocation() {
        return location;
    }

    public Integer getPoiCount() {
        return poiCount;
    }

    public List<Pois> getPois() {
        return pois;
    }

    public void setAdInfo(AdInfo adInfo) {
        this.adInfo = adInfo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddressComponent(AddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public void setAddressReference(AddressReference addressReference) {
        this.addressReference = addressReference;
    }

    public void setFormattedAddresses(FormattedAddresses formattedAddresses) {
        this.formattedAddresses = formattedAddresses;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPoiCount(Integer poiCount) {
        this.poiCount = poiCount;
    }

    public void setPois(List<Pois> pois) {
        this.pois = pois;
    }
}
