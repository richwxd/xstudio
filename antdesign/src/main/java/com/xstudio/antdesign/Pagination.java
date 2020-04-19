package com.xstudio.antdesign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ant design分页插件对象
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     * @param total   总数
     * @param current 当前页
     */
    public Pagination(Integer total, Integer current) {
        this.total = total;
        this.current = current;
    }

}
