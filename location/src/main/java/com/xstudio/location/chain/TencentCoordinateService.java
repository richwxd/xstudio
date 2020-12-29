package com.xstudio.location.chain;

import com.xstudio.core.ApiResponse;
import com.xstudio.location.ApiKey;
import com.xstudio.location.ApiKeyType;
import com.xstudio.location.LngLat;
import com.xstudio.location.result.RegeoResult;
import com.xstudio.location.tencent.TencentWebserviceApi;
import com.xstudio.location.tencent.params.RegeoParams;
import com.xstudio.location.tencent.response.RegeoResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 高德坐标服务
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/21
 */
public class TencentCoordinateService implements Coordinate {
    private final TencentWebserviceApi webserviceApi = new TencentWebserviceApi();
    private final List<ApiKey> keys;
    private ApiKey currentKey;

    public TencentCoordinateService(String domain, List<ApiKey> keys) {
        this.keys = keys;
        webserviceApi.setDomain(domain);
    }

    @Override
    public ApiResponse<RegeoResult> regeo(LngLat lngLat, Coordinate service) {
        for (ApiKey key : keys) {
            if (key.getType().equals(ApiKeyType.TENCENT) && key.getAvailable()) {
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
            regeoResult.setFormattedAddress(data.getResult().getFormattedAddresses().getRecommend());
            result.setData(regeoResult);
        } else {
            result = service.regeo(lngLat, this);
        }

        return result;
    }

    @Override
    public boolean isKeyAvailable(Integer code) {
        return code != 503
                && code != 112
                ;
    }
}
