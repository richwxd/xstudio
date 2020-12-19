package com.xstudio.location.tencent;

import com.xstudio.core.ApiResponse;
import com.xstudio.location.LngLat;
import com.xstudio.location.baidu.BaiduWebserviceApi;
import com.xstudio.location.baidu.params.ReverseGeocodingParams;
import com.xstudio.location.tencent.params.RegeoParams;
import com.xstudio.location.tencent.response.RegeoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TencentWebserviceApiTest {

    @Test
    void regeo() {
        TencentWebserviceApi api = new TencentWebserviceApi("https://apis.map.qq.com", "");
        String s = "116.481488,39.990464";
        String[] split = s.split(",");
        LngLat lngLat = new LngLat(split[0], split[1]);
        RegeoParams params = new RegeoParams(lngLat);
        ApiResponse<RegeoResponse> regeo = api.regeo(params);
        Assertions.assertFalse(regeo.getSuccess());
    }
}