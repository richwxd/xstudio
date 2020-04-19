package com.xstudio.antdesign;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * ant design 表格请求返回对象
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TableResponse<T> implements Serializable {

    private static final long serialVersionUID = 6323923218194260827L;
    /**
     * 表格渲染值
     */
    private List<T> list;

    /**
     * 分页信息
     */
    private Pagination pagination;

    public TableResponse() {
    }

    /**
     * Setter for property 'pagination'.
     *
     * @param total 总数
     * @param page  当前页
     */
    public void setPagination(Integer total, Integer page) {
        pagination = new Pagination();
        this.pagination.setTotal(total);
        this.pagination.setCurrent(page);
    }
}
