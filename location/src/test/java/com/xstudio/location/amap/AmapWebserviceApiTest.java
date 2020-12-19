package com.xstudio.location.amap;

import com.xstudio.core.ApiResponse;
import com.xstudio.location.LngLat;
import com.xstudio.location.amap.params.RegeoParams;
import com.xstudio.location.amap.response.RegeoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmapWebserviceApiTest {

    @Test
    void regeo() {
        AmapWebserviceApi api = new AmapWebserviceApi("https://restapi.amap.com","");
        String s = "116.481488,39.990464";
        String[] split = s.split(",");
        LngLat lngLat = new LngLat(split[0], split[1]);
        RegeoParams params = new RegeoParams(lngLat);
        ApiResponse<RegeoResponse> regeo = api.regeo(params);
        Assertions.assertFalse(regeo.getSuccess());
    }
}