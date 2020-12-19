package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

public class FormattedAddresses {
    /**
     * recommend
     */
    /**
     * recommend : 海淀区中关村中国技术交易大厦(彩和坊路)
     * rough : 海淀区中关村中国技术交易大厦(彩和坊路)
     */

    @SerializedName("recommend")
    private String recommend;
    /**
     * rough
     */
    @SerializedName("rough")
    private String rough;

    public String getRecommend() {
        return recommend;
    }

    public String getRough() {
        return rough;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public void setRough(String rough) {
        this.rough = rough;
    }
}
