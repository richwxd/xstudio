package com.xstudio.location.baidu;

import com.xstudio.core.ApiResponse;
import com.xstudio.location.LngLat;
import com.xstudio.location.baidu.params.ReverseGeocodingParams;
import com.xstudio.location.baidu.response.RegeoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BaiduWebserviceApiTest {

    @Test
    void regeo() {
        BaiduWebserviceApi api = new BaiduWebserviceApi("http://api.map.baidu.com", "");
        String s = "116.481488,39.990464";
        String[] split = s.split(",");
        LngLat lngLat = new LngLat(split[0], split[1]);
        ReverseGeocodingParams params = new ReverseGeocodingParams(lngLat);
        ApiResponse<RegeoResponse> regeo = api.regeo(params);
        Assertions.assertFalse(regeo.getSuccess());
    }
}