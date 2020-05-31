package com.xstudio.antdesign;

import java.io.Serializable;

/**
 * ant design 照片墙返回对象
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
public class PhotoWall implements Serializable {

    private static final long serialVersionUID = -3107187952595288288L;

    /**
     * 图片ID
     */
    private String uid;

    /**
     * 图片名称
     */
    private String name;

    /**
     * 上传状态
     */
    private String status = "done";

    /**
     * 图片下载地址
     */
    private String url;

    /**
     * 缩略图地址
     */
    private String thumbUrl;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public PhotoWall() {
    }

    /**
     * @param uid 图片ID
     * @param name 图片名称
     * @param url 图片地址
     */
    public PhotoWall(String uid, String name, String url) {
        this.uid = uid;
        this.name = name;
        this.url = url;
    }

    /**
     * @param uid 图片ID
     * @param url 图片地址
     */
    public PhotoWall(String uid, String url) {
        this.uid = uid;
        this.url = url;
    }
}
