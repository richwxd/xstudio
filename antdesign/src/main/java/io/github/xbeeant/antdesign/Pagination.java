package io.github.xbeeant.antdesign;

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

    /**
     * get field 数据总数
     *
     * @return total 数据总数
     */
    public Integer getTotal() {
        return this.total;
    }

    /**
     * set field 数据总数
     *
     * @param total 数据总数
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * get field 当前页 从0开始计数
     *
     * @return current 当前页 从0开始计数
     */
    public Integer getCurrent() {
        return this.current;
    }

    /**
     * set field 当前页 从0开始计数
     *
     * @param current 当前页 从0开始计数
     */
    public void setCurrent(Integer current) {
        this.current = current;
    }

    /**
     * get field 每页显示的数据条数
     *
     * @return pageSize 每页显示的数据条数
     */
    public Integer getPageSize() {
        return this.pageSize;
    }

    /**
     * set field 每页显示的数据条数
     *
     * @param pageSize 每页显示的数据条数
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
