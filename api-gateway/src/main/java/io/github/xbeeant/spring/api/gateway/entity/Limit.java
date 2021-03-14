package io.github.xbeeant.spring.api.gateway.entity;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaobiao
 * @version 2020/2/28
 */

public class Limit {
    int times = 10;

    TimeUnit unit = TimeUnit.SECONDS;

    public int getTimes() {
        return times;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public Limit(int times, TimeUnit unit) {
        this.times = times;
        this.unit = unit;
    }
}
