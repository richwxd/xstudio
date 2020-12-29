package com.xstudio.location.chain;


import com.xstudio.core.ApiResponse;
import com.xstudio.location.LngLat;
import com.xstudio.location.result.RegeoResult;

/**
 * @author xiaobiao
 * @date 2020/12/21
 */
public interface Coordinate {
    /**
     * 关键是否还可用
     *
     * @param code 返回码
     * @return boolean
     */
    boolean isKeyAvailable(Integer code);

    /**
     * 反解析
     *
     * @param lngLat  经纬度
     * @param service 服务
     * @return {@link ApiResponse<RegeoResult>}
     */
    ApiResponse<RegeoResult> regeo(LngLat lngLat, Coordinate service);
}
