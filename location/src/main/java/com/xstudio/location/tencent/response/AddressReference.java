package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

public class AddressReference {
    /**
     * businessArea
     */
    /**
     * business_area : {"id":"14178584199053362783","title":"中关村","location":{"lat":39.980598,"lng":116.310997},"_distance":0,"_dir_desc":"内"}
     * famous_area : {"id":"14178584199053362783","title":"中关村","location":{"lat":39.980598,"lng":116.310997},"_distance":0,"_dir_desc":"内"}
     * crossroad : {"id":"529979","title":"海淀大街/彩和坊路(路口)","location":{"lat":39.982498,"lng":116.30809},"_distance":185.8,"_dir_desc":"北"}
     * town : {"id":"110108012","title":"海淀街道","location":{"lat":39.974819,"lng":116.284409},"_distance":0,"_dir_desc":"内"}
     * street_number : {"id":"595672509379194165901290","title":"北四环西路66号","location":{"lat":39.984089,"lng":116.308037},"_distance":45.8,"_dir_desc":""}
     * street : {"id":"9217092216709107946","title":"彩和坊路","location":{"lat":39.980335,"lng":116.308311},"_distance":46.6,"_dir_desc":"西"}
     * landmark_l2 : {"id":"3629720141162880123","title":"中国技术交易大厦","location":{"lat":39.984253,"lng":116.307472},"_distance":0,"_dir_desc":"内"}
     */

    @SerializedName("business_area")
    private BusinessArea businessArea;
    /**
     * crossroad
     */
    @SerializedName("crossroad")
    private Crossroad crossroad;
    /**
     * famousArea
     */
    @SerializedName("famous_area")
    private FamousArea famousArea;
    /**
     * landmarkL2
     */
    @SerializedName("landmark_l2")
    private LandmarkL2 landmarkL2;
    /**
     * street
     */
    @SerializedName("street")
    private Street street;
    /**
     * streetNumber
     */
    @SerializedName("street_number")
    private StreetNumber streetNumber;
    /**
     * town
     */
    @SerializedName("town")
    private Town town;

    public BusinessArea getBusinessArea() {
        return businessArea;
    }

    public Crossroad getCrossroad() {
        return crossroad;
    }

    public FamousArea getFamousArea() {
        return famousArea;
    }

    public LandmarkL2 getLandmarkL2() {
        return landmarkL2;
    }

    public Street getStreet() {
        return street;
    }

    public StreetNumber getStreetNumber() {
        return streetNumber;
    }

    public Town getTown() {
        return town;
    }

    public void setBusinessArea(BusinessArea businessArea) {
        this.businessArea = businessArea;
    }

    public void setCrossroad(Crossroad crossroad) {
        this.crossroad = crossroad;
    }

    public void setFamousArea(FamousArea famousArea) {
        this.famousArea = famousArea;
    }

    public void setLandmarkL2(LandmarkL2 landmarkL2) {
        this.landmarkL2 = landmarkL2;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public void setStreetNumber(StreetNumber streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
