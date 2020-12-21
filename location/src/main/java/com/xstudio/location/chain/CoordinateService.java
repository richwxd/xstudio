package com.xstudio.location.chain;

import com.xstudio.core.ApiResponse;
import com.xstudio.location.LngLat;
import com.xstudio.location.result.RegeoResult;

public abstract class CoordinateService {
    private String domain;
    private String key;

    public CoordinateService(String domain, String key) {
        this.domain = domain;
        this.key = key;
    }

    public abstract ApiResponse<RegeoResult> regeo(LngLat lngLat, CoordinateService service);
}
