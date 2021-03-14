package io.github.xbeeant.spring.mybatis.pagehelper;

import com.github.pagehelper.PageRowBounds;

/**
 * 分页对象
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
public class PageBounds extends PageRowBounds {

    /**
     * 排序
     */
    private String orders;

    /**
     * 当前页
     */
    private Integer page = 1;

    public PageBounds(int offset, int limit) {
        super(offset, limit);
    }


    /**
     * get field 排序
     *
     * @return orders 排序
     */
    public String getOrders() {
        return this.orders;
    }

    /**
     * get field 当前页
     *
     * @return page 当前页
     */
    public Integer getPage() {
        return this.page;
    }

    /**
     * set field 排序
     *
     * @param orders 排序
     */
    public void setOrders(String orders) {
        this.orders = orders;
    }

    /**
     * set field 当前页
     *
     * @param page 当前页
     */
    public void setPage(Integer page) {
        this.page = page;
    }
}
