package com.xstudio.aop.entity;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 2020/2/17
 */
public class UserAgent implements Serializable {
    private String family;

    private String version;

    private String os;

    private String osVersion;

    private String device;

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

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
