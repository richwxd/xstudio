package com.xstudio.location.chain;

import com.xstudio.core.ApiResponse;
import com.xstudio.location.LngLat;
import com.xstudio.location.baidu.BaiduWebserviceApi;
import com.xstudio.location.baidu.params.ReverseGeocodingParams;
import com.xstudio.location.baidu.response.RegeoResponse;
import com.xstudio.location.result.RegeoResult;

/**
 * 百度坐标服务
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/21
 */
public class BaiduCoordinateService extends CoordinateService {
    private BaiduWebserviceApi baiduWebserviceApi = new BaiduWebserviceApi();

    public BaiduCoordinateService(String domain, String key) {
        super(domain, key);
        baiduWebserviceApi.setAk(key);
        baiduWebserviceApi.setDomain(domain);
    }

    @Override
    public ApiResponse<RegeoResult> regeo(LngLat lngLat, CoordinateService service) {
        ApiResponse<RegeoResult> result = new ApiResponse<>();
        ReverseGeocodingParams params = new ReverseGeocodingParams();
        params.setLocation(lngLat);
        ApiResponse<RegeoResponse> regeoReponse = baiduWebserviceApi.regeo(params);
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


}
