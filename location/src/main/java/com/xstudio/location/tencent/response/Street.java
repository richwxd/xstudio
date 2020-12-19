package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

public class Street {
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
     * id : 9217092216709107946
     * title : 彩和坊路
     * location : {"lat":39.980335,"lng":116.308311}
     * _distance : 46.6
     * _dir_desc : 西
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
