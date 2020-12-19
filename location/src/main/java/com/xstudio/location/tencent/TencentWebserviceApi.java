package com.xstudio.location.tencent;

import com.xstudio.core.ApiResponse;
import com.xstudio.core.ErrorCodeConstant;
import com.xstudio.core.JsonUtil;
import com.xstudio.http.ClientResponse;
import com.xstudio.http.RequestUtil;
import com.xstudio.location.LngLat;
import com.xstudio.location.tencent.params.RegeoParams;
import com.xstudio.location.tencent.response.RegeoResponse;
import com.xstudio.location.tencent.response.RegeoResult;
import com.xstudio.location.tencent.response.TencentStatusInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯地图服务API
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
@SuppressWarnings("unused")
public class TencentWebserviceApi {
    private String domain = "";

    private String key = "";

    public TencentWebserviceApi() {
    }

    public TencentWebserviceApi(String domain, String key) {
        this.domain = domain;
        this.key = key;
    }

    /**
     * 逆地理编码
     *
     * @param regeoParams regeo参数
     * @return {@link ApiResponse< RegeoResult >}
     */
    public ApiResponse<RegeoResponse> regeo(RegeoParams regeoParams) {
        ApiResponse<RegeoResponse> result = new ApiResponse<>();
        Map<String, Object> params = new HashMap<>();

        params.put("key", this.key);
        LngLat location = regeoParams.getLocation();
        params.put("location", location.getLat() + "," + location.getLng());
        params.put("output", regeoParams.getOutput());
        params.put("get_poi", regeoParams.getGetPoi() ? 1 : 0);

        ClientResponse clientResponse = RequestUtil.get(domain + UrlConstant.REGEO, RequestUtil.getCanonicalQueryString(params));
        if (null == clientResponse) {
            result.setResult(ErrorCodeConstant.API_INVALID, ErrorCodeConstant.API_INVALID_MSG);
            return result;
        }

        RegeoResponse geoResponse = JsonUtil.toObject(clientResponse.getContent(), RegeoResponse.class);
        if (0 != geoResponse.getStatus()) {
            result.setResult(geoResponse.getStatus(), TencentStatusInfo.getDescription(geoResponse.getStatus()));
            return result;
        }
        result.setData(geoResponse);
        return result;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
