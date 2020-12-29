package com.xstudio.location.chain;


import com.xstudio.core.ApiResponse;
import com.xstudio.location.ApiKey;
import com.xstudio.location.ApiKeyType;
import com.xstudio.location.LngLat;
import com.xstudio.location.amap.AmapWebserviceApi;
import com.xstudio.location.amap.params.RegeoParams;
import com.xstudio.location.amap.response.RegeoResponse;
import com.xstudio.location.result.RegeoResult;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 高德坐标服务
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/21
 */
public class AmapCoordinateService implements Coordinate {
    private final AmapWebserviceApi webserviceApi = new AmapWebserviceApi();
    private final List<ApiKey> keys;
    private ApiKey currentKey;

    public AmapCoordinateService(String domain, List<ApiKey> keys) {
        webserviceApi.setDomain(domain);
        this.keys = keys;
    }

    @Override
    public ApiResponse<RegeoResult> regeo(LngLat lngLat, Coordinate service) {
        for (ApiKey key : keys) {
            if (key.getType().equals(ApiKeyType.AMAP) && key.getAvailable()) {
                currentKey = key;
                break;
            }
        }
        if (null == currentKey) {
            return service.regeo(lngLat, this);
        }
        webserviceApi.setKey(currentKey.getKey());

        if (StringUtils.isEmpty(webserviceApi.getKey())) {
            return service.regeo(lngLat, this);
        }

        ApiResponse<RegeoResult> result = new ApiResponse<>();
        RegeoParams params = new RegeoParams();
        params.setLocation(lngLat);
        ApiResponse<RegeoResponse> regeoReponse = webserviceApi.regeo(params);

        currentKey.setAvailable(isKeyAvailable(regeoReponse.getCode()));

        if (null != regeoReponse.getData()) {
            RegeoResponse data = regeoReponse.getData();
            RegeoResult regeoResult = new RegeoResult();
            regeoResult.setFormattedAddress(data.getRegeocode().getFormattedAddress());
            result.setData(regeoResult);
        } else {
            result = service.regeo(lngLat, this);
        }

        return result;
    }

    @Override
    public boolean isKeyAvailable(Integer code) {
        return 10001 != code
                && 10003 != code
                && 10009 != code
                && 10013 != code
                && 10026 != code
                && 10044 != code
                && 10045 != code
                && 40002 != code
                && 40003 != code;
    }
}
