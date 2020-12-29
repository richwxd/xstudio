package com.xstudio.location;

/**
 * 地图服务的key
 *
 * @author xiaobiao
 * @date 2020/12/21
 */
public class ApiKey {
    private Boolean available = true;
    private String key;
    private ApiKeyType type;

    public ApiKey(String key, ApiKeyType type) {
        this.key = key;
        this.type = type;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ApiKeyType getType() {
        return type;
    }

    public void setType(ApiKeyType type) {
        this.type = type;
    }
}
