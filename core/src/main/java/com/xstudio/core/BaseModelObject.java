package com.xstudio.core;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

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
    private Date createAt;
    /**
     * 创建开始时间
     */
    @Expose(serialize = false)
    private Date createAtBegin;
    /**
     * 结束开始时间
     */
    @Expose(serialize = false)
    private Date createAtEnd;
    /**
     * 创建人ID
     */
    private String createBy;
    /**
     * 更新时间
     */
    private Date updateAt;
    /**
     * 更新开始时间
     */
    @Expose(serialize = false)
    private Date updateAtBegin;
    /**
     * 更新结束时间
     */
    @Expose(serialize = false)
    private Date updateAtEnd;
    /**
     * 更新人ID
     */
    private String updateBy;

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public K getKey() {
        return valueOfKey();
    }

    /**
     * 获取主键
     *
     * @return K
     */
    public abstract K valueOfKey();

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
