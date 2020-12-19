package com.xstudio.location.tencent.response;

import com.xstudio.location.baidu.response.BaiduStatusInfo;

public enum TencentStatusInfo {
    C310("请求参数信息有误"),
    C311("Key格式错误"),
    C306("请求有护持信息请检查字符串"),
    C110("请求来源未被授权");

    private String description;


    public static String getDescription(Integer infoCode) {
        TencentStatusInfo[] values = TencentStatusInfo.values();
        for (TencentStatusInfo value : values) {
            if (value.name().equals("C" + infoCode)) {
                return value.getDescription();
            }
        }

        return "";
    }

    public String getDescription() {
        return description;
    }

    TencentStatusInfo(String description) {
        this.description = description;
    }
}
