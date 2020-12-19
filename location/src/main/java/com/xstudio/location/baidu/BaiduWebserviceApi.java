package com.xstudio.location.baidu;

import com.xstudio.core.ApiResponse;
import com.xstudio.core.ErrorCodeConstant;
import com.xstudio.core.JsonUtil;
import com.xstudio.http.ClientResponse;
import com.xstudio.http.RequestUtil;
import com.xstudio.location.LngLat;
import com.xstudio.location.baidu.params.ReverseGeocodingParams;
import com.xstudio.location.baidu.response.BaiduStatusInfo;
import com.xstudio.location.baidu.response.RegeoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度地图服务API
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
@SuppressWarnings("unused")
public class BaiduWebserviceApi {
    private String ak = "";
    private String domain = "";

    public BaiduWebserviceApi() {
    }

    public BaiduWebserviceApi(String domain, String key) {
        this.domain = domain;
        this.ak = key;
    }

    /**
     * 逆地理编码
     *
     * @param regeoParams regeo参数
     * @return {@link ApiResponse<RegeoResponse>}
     */
    public ApiResponse<RegeoResponse> regeo(ReverseGeocodingParams regeoParams) {
        ApiResponse<RegeoResponse> result = new ApiResponse<>();
        Map<String, Object> params = new HashMap<>();

        params.put("ak", this.ak);
        LngLat location = regeoParams.getLocation();
        params.put("location", location.getLat() + "," + location.getLng());
        params.put("callback", regeoParams.getCallback());
        params.put("output", regeoParams.getOutput());
        params.put("radius", regeoParams.getRadius());

        ClientResponse clientResponse = RequestUtil.get(domain + UrlConstant.REGEO, RequestUtil.getCanonicalQueryString(params));
        if (null == clientResponse) {
            result.setResult(ErrorCodeConstant.API_INVALID, ErrorCodeConstant.API_INVALID_MSG);
            return result;
        }

        RegeoResponse geoResponse = JsonUtil.toObject(clientResponse.getContent(), RegeoResponse.class);
        if (0 != geoResponse.getStatus()) {
            result.setResult(geoResponse.getStatus(), BaiduStatusInfo.getDescription(geoResponse.getStatus()));
            return result;
        }
        result.setData(geoResponse);
        return result;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
