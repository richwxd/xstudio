package com.xstudio.location.tencent.response;

import com.google.gson.annotations.SerializedName;

/**
 * 坐标反解析结果
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class RegeoResponse extends TencentResponse {
    /**
     * result
     */
    @SerializedName("result")
    private RegeoResult result;

    /**
     * 获取 result.
     *
     * @return result 值
     */
    public RegeoResult getResult() {
        return result;
    }

    /**
     * 设置 result.
     *
     * <p>通过 getResult() 获取 result</p>
     *
     * @param result result
     */
    public void setResult(RegeoResult result) {
        this.result = result;
    }
}
