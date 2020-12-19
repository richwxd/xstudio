package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

public class StreetNumber {
    /**
     * dirDesc
     */
    @SerializedName("_dir_desc")
    private String dirDesc;
    /**
     * distance
     */
    @SerializedName("_distance")
    private Double distance;
    /**
     * id
     */
    /**
     * id : 595672509379194165901290
     * title : 北四环西路66号
     * location : {"lat":39.984089,"lng":116.308037}
     * _distance : 45.8
     * _dir_desc :
     */

    @SerializedName("id")
    private String id;
    /**
     * location
     */
    @SerializedName("location")
    private Location location;
    /**
     * title
     */
    @SerializedName("title")
    private String title;

    public String getDirDesc() {
        return dirDesc;
    }

    public Double getDistance() {
        return distance;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public void setDirDesc(String dirDesc) {
        this.dirDesc = dirDesc;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
