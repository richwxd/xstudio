package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

public class Crossroad {
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
     * id : 529979
     * title : 海淀大街/彩和坊路(路口)
     * location : {"lat":39.982498,"lng":116.30809}
     * _distance : 185.8
     * _dir_desc : 北
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
