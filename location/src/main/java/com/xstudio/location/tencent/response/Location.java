package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

public class Location {
    /**
     * lat
     */
    @SerializedName("lat")
    private Double lat;
    /**
     * lng
     */
    @SerializedName("lng")
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
