package com.xstudio.location.result;

/**
 * 坐标反解析结果
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/20
 */
public class RegeoResult {
    private String formattedAddress;

    /**
     * 获取 formattedAddress.
     *
     * @return formattedAddress 值
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * 设置 formattedAddress.
     *
     * <p>通过 getFormattedAddress() 获取 formattedAddress</p>
     *
     * @param formattedAddress formattedAddress
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
