package com.xstudio.antdesign;

import java.io.Serializable;

/**
 * ant design分页插件对象
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
public class Pagination implements Serializable {
    private static final long serialVersionUID = -9122189141426414394L;
    /**
     * 数据总数
     */
    private Integer total;
    /**
     * 当前页, 从0开始计数
     */
    private Integer current;

    /**
     * 每页显示的数据条数
     */
    private Integer pageSize;

    public Pagination() {
    }

    /**
     * @param total   总数
     * @param current 当前页
     */
    public Pagination(Integer total, Integer current) {
        this.total = total;
        this.current = current;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
