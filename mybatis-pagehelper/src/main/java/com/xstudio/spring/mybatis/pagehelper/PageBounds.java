package com.xstudio.spring.mybatis.pagehelper;

import com.github.pagehelper.PageRowBounds;

/**
 * 分页对象
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
public class PageBounds extends PageRowBounds {

    private String orders;

    private Integer page = 1;

    public PageBounds(int offset, int limit) {
        super(offset, limit);
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
