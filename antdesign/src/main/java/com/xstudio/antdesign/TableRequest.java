package com.xstudio.antdesign;

import lombok.Data;

import java.io.Serializable;

/**
 * ant design 表格请求参数
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
@Data
public class TableRequest implements Serializable {
    private static final long serialVersionUID = -3783512820125403682L;
    /**
     * 当前页
     */
    private Integer currentPage = 0;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String sorter = "";
}
