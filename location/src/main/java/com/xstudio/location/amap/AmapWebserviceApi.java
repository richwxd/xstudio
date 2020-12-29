package com.xstudio.location.amap;

import com.xstudio.core.ApiResponse;
import com.xstudio.core.ErrorCodeConstant;
import com.xstudio.core.JsonUtil;
import com.xstudio.http.ClientResponse;
import com.xstudio.http.RequestUtil;
import com.xstudio.location.LngLat;
import com.xstudio.location.amap.params.RegeoExtensionsParams;
import com.xstudio.location.amap.params.RegeoParams;
import com.xstudio.location.amap.response.RegeoResponse;
import com.xstudio.location.amap.response.ResponseInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 高德地图服务API
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
@SuppressWarnings("unused")
public class AmapWebserviceApi {
    private String domain = "";

    private String key = "";

    /**
     * 获取 key.
     *
     * @return key 值
     */
    public String getKey() {
        return key;
    }

    public AmapWebserviceApi() {
    }

    public AmapWebserviceApi(String domain, String key) {
        this.domain = domain;
        this.key = key;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 逆地理编码
     *
     * @param regeoParams regeo参数
     * @return {@link ApiResponse<RegeoResponse>}
     */
    public ApiResponse<RegeoResponse> regeo(RegeoParams regeoParams) {
        ApiResponse<RegeoResponse> result = new ApiResponse<>();
        Map<String, Object> params = new HashMap<>();

        params.put("key", this.key);
        LngLat location = regeoParams.getLocation();
        params.put("location", location.getLng() + "," + location.getLat());
        params.put("batch", regeoParams.getBatch());
        params.put("callback", regeoParams.getCallback());
        params.put("extensions", regeoParams.getExtensions());
        params.put("output", regeoParams.getOutput());
        params.put("sig", regeoParams.getSig());
        params.put("radius", regeoParams.getRadius());

        if (regeoParams instanceof RegeoExtensionsParams) {
            RegeoExtensionsParams extensionsParams = (RegeoExtensionsParams) regeoParams;
            params.put("homeorcorp", extensionsParams.getHomeorcorp());
            params.put("poitype", extensionsParams.getPoitype());
            params.put("roadlevel", extensionsParams.getRoadlevel());
        }

        ClientResponse clientResponse = RequestUtil.get(domain + UrlConstant.REGEO, RequestUtil.getCanonicalQueryString(params));
        if (null == clientResponse) {
            result.setResult(ErrorCodeConstant.API_INVALID, ErrorCodeConstant.API_INVALID_MSG);
            return result;
        }

        RegeoResponse geoResponse = JsonUtil.toObject(clientResponse.getContent(), RegeoResponse.class);
        if ("0".equals(geoResponse.getStatus())) {
            result.setResult(geoResponse.getInfocode(), ResponseInfo.getDescription(geoResponse.getInfocode()));
            return result;
        }
        result.setData(geoResponse);
        return result;
    }
}
