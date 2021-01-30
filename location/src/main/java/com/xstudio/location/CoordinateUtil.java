package com.xstudio.location;

import com.xstudio.core.ApiResponse;
import com.xstudio.location.chain.AmapCoordinateService;
import com.xstudio.location.chain.CoordinateChain;
import com.xstudio.location.result.RegeoResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 位置WebService服务
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/20
 */
public class CoordinateUtil {
    private final static List<CoordinateChain> services = new ArrayList<>();

    static {
        services.add(new AmapCoordinateService("",""));
        services.add(new AmapCoordinateService("",""));
        services.add(new AmapCoordinateService("",""));
    }

    public static ApiResponse<RegeoResult> regeo(LngLat lngLat) {

    }
}
