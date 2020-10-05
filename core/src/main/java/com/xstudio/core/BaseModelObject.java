package com.xstudio.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xstudio.core.date.DateTime;

import java.io.Serializable;

/**
 * 基础model对象
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
public abstract class BaseModelObject<K> implements Serializable {

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime createAt;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime updateAt;

    /**
     * 更新人ID
     */
    private String updateBy;


    public K getKey() {
        return valueOfKey();
    }

    /**
     * 获取主键
     *
     * @return K
     */
    public abstract K valueOfKey();


    /**
     * 设置主键值
     *
     * @param value 主键值
     */
    public abstract void assignKeyValue(K value);

    /**
     * 清除铭感信息：<br>
     * 创建人ID<br>
     * 更新人ID<br>
     * 创建时间<br>
     * 更新时间<br>
     * 租户ID<br>
     */
    public void emptySensitiveInfo() {
        setCreateBy(null);
        setCreateAt(null);
        setUpdateBy(null);
        setUpdateAt(null);
    }

    public DateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(DateTime createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public DateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(DateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
