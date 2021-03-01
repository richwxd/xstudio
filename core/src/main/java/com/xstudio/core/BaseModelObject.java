package com.xstudio.core;

import com.xstudio.core.date.DateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础model对象
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
@ApiModel("基础对象")
public abstract class BaseModelObject<K> implements Serializable {

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建日期", name = "createAt", dataType = "Date", example = "2021-01-00 13:00:21", allowableValues = "")
    private DateTime createAt;

    /**
     * 创建人ID
     */
    @ApiModelProperty(value = "创建人ID", name = "createBy", dataType = "String", example = "2019282716253425", allowableValues = "")
    private String createBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新日期", name = "updateAt", dataType = "Date", example = "2021-01-00 13:00:21", allowableValues = "")
    private DateTime updateAt;

    /**
     * 更新人ID
     */
    @ApiModelProperty(value = "更新人ID", name = "updateBy", dataType = "String", example = "2019282716253425", allowableValues = "")
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

    public void setCreateAt(DateTime createAt) {
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
