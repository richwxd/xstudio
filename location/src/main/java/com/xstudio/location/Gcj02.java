package com.xstudio.location;

/**
 * Gcj02坐标系，即火星坐标系，WGS84坐标系经加密后的坐标系。例如：腾讯 高德
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
@SuppressWarnings("unused")
public class Gcj02 extends LngLat{
    private static final long serialVersionUID = -2437414102015120614L;

    /**
     * @param lng 经度
     * @param lat 纬度
     */
    public Gcj02(Double lng, Double lat) {
        super(lng, lat);
    }

    /**
     * 获取BD09坐标
     *
     * @return {@link LngLat}
     */
    public Bd09 toBd09() {
        LngLat lngLat = CoordinateTransformer.gcj02ToBd09(this.getLng(), this.getLat());
        return new Bd09(lngLat.getLng(), lngLat.getLat());
    }

    /**
     * 获取WGS84坐标
     *
     * @return {@link LngLat}
     */
    public Wgs84 toWgs84() {
        LngLat lngLat = CoordinateTransformer.gcj02ToWgs84(this.getLng(), this.getLat());
        return new Wgs84(lngLat.getLng(), lngLat.getLat());
    }

    /**
     * 获取WGS84坐标(精确）
     *
     * @return {@link LngLat}
     */
    public Wgs84 toWgs84Exactly() {
        LngLat lngLat = CoordinateTransformer.gcj02ToWgs84Exactly(this.getLng(), this.getLat());
        return new Wgs84(lngLat.getLng(), lngLat.getLat());
    }
}
