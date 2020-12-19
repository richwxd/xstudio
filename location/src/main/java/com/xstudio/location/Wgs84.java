package com.xstudio.location;

/**
 * WGS84坐标系：即地球坐标系，国际上通用的坐标系
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/19
 */
public class Wgs84 extends LngLat {

    private static final long serialVersionUID = -5001987658580481428L;

    /**
     * @param lng 经度
     * @param lat 纬度
     */
    public Wgs84(Double lng, Double lat) {
        super(lng, lat);
    }

    /**
     * 获取BD09坐标
     *
     * @return {@link LngLat}
     */
    public Bd09 toBd09() {
        LngLat lngLat = CoordinateTransformer.wgs84ToBd09(this.getLng(), this.getLat());
        return new Bd09(lngLat.getLng(), lngLat.getLat());
    }

    /**
     * 获取GCJ02坐标
     *
     * @return {@link LngLat}
     */
    public Gcj02 toGcj02() {
        LngLat lngLat = CoordinateTransformer.wgs84ToGcj02(this.getLng(), this.getLat());
        return new Gcj02(lngLat.getLng(), lngLat.getLat());
    }
}
