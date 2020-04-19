package com.xstudio.spring.api.gateway.entity;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaobiao
 * @version 2020/2/28
 */

public class Limit {
    @Getter
    int times = 10;

    @Getter
    TimeUnit unit = TimeUnit.SECONDS;

    public Limit(int times, TimeUnit unit) {
        this.times = times;
        this.unit = unit;
    }
}
