package com.xstudio.location.chain;


import com.xstudio.core.ApiResponse;
import com.xstudio.location.ApiKey;
import com.xstudio.location.ApiKeyType;
import com.xstudio.location.LngLat;
import com.xstudio.location.baidu.BaiduWebserviceApi;
import com.xstudio.location.baidu.params.ReverseGeocodingParams;
import com.xstudio.location.baidu.response.RegeoResponse;
import com.xstudio.location.result.RegeoResult;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 百度坐标服务
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/21
 */
public class BaiduCoordinateService implements Coordinate {
    private final BaiduWebserviceApi webserviceApi = new BaiduWebserviceApi();
    private final List<ApiKey> keys;
    private ApiKey currentKey;

    public BaiduCoordinateService(String domain, List<ApiKey> keys) {
        webserviceApi.setDomain(domain);
        this.keys = keys;
    }

    @Override
    public ApiResponse<RegeoResult> regeo(LngLat lngLat, Coordinate service) {
        for (ApiKey key : keys) {
            if (key.getType().equals(ApiKeyType.BAIDU) && key.getAvailable()) {
                currentKey = key;
                break;
            }
        }
        if (null == currentKey) {
            return service.regeo(lngLat, this);
        }

        webserviceApi.setAk(currentKey.getKey());

        if (StringUtils.isEmpty(webserviceApi.getAk())) {
            return service.regeo(lngLat, this);
        }

        ApiResponse<RegeoResult> result = new ApiResponse<>();
        ReverseGeocodingParams params = new ReverseGeocodingParams();
        params.setLocation(lngLat);
        ApiResponse<RegeoResponse> regeoReponse = webserviceApi.regeo(params);

        currentKey.setAvailable(isKeyAvailable(regeoReponse.getCode()));
        if (null != regeoReponse.getData()) {
            RegeoResponse data = regeoReponse.getData();
            RegeoResult regeoResult = new RegeoResult();
            regeoResult.setFormattedAddress(data.getResult().getFormattedAddress());
            result.setData(regeoResult);
        } else {
            result = service.regeo(lngLat, this);
        }

        return result;
    }

    @Override
    public boolean isKeyAvailable(Integer code) {
        return code != 201
                && code != 202
                && code != 203
                && code != 240
                && code != 250
                && code != 251
                && code != 252
                && code != 261
                && code != 301
                && code != 302
                ;
    }
}
