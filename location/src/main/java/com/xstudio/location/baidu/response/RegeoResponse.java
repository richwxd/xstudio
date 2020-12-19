package com.xstudio.location.baidu.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class RegeoResponse extends BaiduResponse {
    /**
     * result
     */
    @SerializedName("result")
    private RegeoResult result;

    public RegeoResult getResult() {
        return result;
    }


    public void setResult(RegeoResult result) {
        this.result = result;
    }
}
