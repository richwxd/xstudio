package com.xstudio.location.chain;

import com.xstudio.core.ApiResponse;
import com.xstudio.location.LngLat;
import com.xstudio.location.result.RegeoResult;
import com.xstudio.location.tencent.TencentWebserviceApi;
import com.xstudio.location.tencent.params.RegeoParams;
import com.xstudio.location.tencent.response.RegeoResponse;

/**
 * 高德坐标服务
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/21
 */
public class TencentCoordinateService extends CoordinateService {
    private TencentWebserviceApi webserviceApi = new TencentWebserviceApi();

    public TencentCoordinateService(String domain, String key) {
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
            regeoResult.setFormattedAddress(data.getResult().getFormattedAddresses().getRecommend());
            result.setData(regeoResult);
        } else {
            result = service.regeo(lngLat, this);
        }

        return result;
    }


}
