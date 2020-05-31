package com.xstudio.antdesign;

import java.io.Serializable;

/**
 * ant design 表格请求参数
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSorter() {
        return sorter;
    }

    public void setSorter(String sorter) {
        this.sorter = sorter;
    }
}
