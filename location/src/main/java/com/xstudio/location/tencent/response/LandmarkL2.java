package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

public class LandmarkL2 {
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
