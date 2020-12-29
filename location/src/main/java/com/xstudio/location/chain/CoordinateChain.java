package com.xstudio.location.chain;

import com.xstudio.core.ApiResponse;
import com.xstudio.core.ErrorCodeConstant;
import com.xstudio.location.LngLat;
import com.xstudio.location.result.RegeoResult;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xiaobiao
 * @date 2020/12/21
 */
public class CoordinateChain implements Coordinate {
    List<Coordinate> services = new LinkedList<>();
    private int index = 0;

    public Coordinate addService(Coordinate service) {
        services.add(service);
        return this;
    }

    @Override
    public ApiResponse<RegeoResult> regeo(LngLat lngLat, Coordinate service) {
        ApiResponse<RegeoResult> msg = new ApiResponse<>();
        if (index >= services.size()) {
            msg.setResult(ErrorCodeConstant.SERVICE_INVALID, ErrorCodeConstant.SERVICE_INVALID_MSG);
            return msg;
        }
        Coordinate filter1 = services.get(index);
        index++;

        return filter1.regeo(lngLat, this);
    }

    @Override
    public boolean isKeyAvailable(Integer code) {
        return false;
    }
}
