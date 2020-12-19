package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

public class BusinessArea {
    /**
     * dirDesc
     */
    @SerializedName("_dir_desc")
    private String dirDesc;
    /**
     * distance
     */
    @SerializedName("_distance")
    private Integer distance;
    /**
     * id
     */
    /**
     * id : 14178584199053362783
     * title : 中关村
     * location : {"lat":39.980598,"lng":116.310997}
     * _distance : 0
     * _dir_desc : 内
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

    public Integer getDistance() {
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

    public void setDistance(Integer distance) {
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
