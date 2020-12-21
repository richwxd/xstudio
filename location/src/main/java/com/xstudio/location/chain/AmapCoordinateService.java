package com.xstudio.location.chain;

import com.xstudio.core.ApiResponse;
import com.xstudio.location.LngLat;
import com.xstudio.location.amap.AmapWebserviceApi;
import com.xstudio.location.amap.params.RegeoParams;
import com.xstudio.location.amap.response.RegeoResponse;
import com.xstudio.location.result.RegeoResult;

/**
 * 高德坐标服务
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/21
 */
public class AmapCoordinateService extends CoordinateService {
    private AmapWebserviceApi webserviceApi = new AmapWebserviceApi();

    public AmapCoordinateService(String domain, String key) {
        super(domain, key);
        webserviceApi.setKey(key);
        webserviceApi.setDomain(domain);
    }

    @Override
    public ApiResponse<RegeoResult> regeo(LngLat lngLat, CoordinateService service) {
        ApiResponse<RegeoResult> result = new ApiResponse<>();
        RegeoParams params = new RegeoParams();
        params.setLocation(lngLat);
        ApiResponse<RegeoResponse> regeoReponse = webserviceApi.regeo(params);
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


}
