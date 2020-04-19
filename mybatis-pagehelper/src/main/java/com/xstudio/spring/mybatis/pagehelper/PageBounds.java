package com.xstudio.spring.mybatis.pagehelper;

import com.github.pagehelper.PageRowBounds;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页对象
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
public class PageBounds extends PageRowBounds {
    @Getter
    @Setter
    private String orders;

    @Getter
    @Setter
    private Integer page = 1;

    public PageBounds(int offset, int limit) {
        super(offset, limit);
    }


}
