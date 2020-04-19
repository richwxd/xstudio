package com.xstudio.aop.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 2020/2/17
 */
@Data
public class UserAgent implements Serializable {
    private String family;

    private String version;

    private String os;

    private String osVersion;

    private String device;

    @Override
    public String toString() {
        return "{" +
                "family='" + family + '\'' +
                ", version='" + version + '\'' +
                ", os='" + os + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", device='" + device + '\'' +
                '}';
    }
}
