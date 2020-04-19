package com.xstudio.spring.mybatis.antdesign;

import com.xstudio.antdesign.TableRequest;
import org.apache.ibatis.session.RowBounds;

/**
 * 分页请求
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
public class PageRequest extends TableRequest {
    private static final long serialVersionUID = 5608398589200527264L;

    public PageRequest() {
    }

    public PageRequest(TableRequest request) {
        this.setCurrentPage(request.getCurrentPage());
        this.setPageSize(request.getPageSize());
        this.setSorter(request.getSorter());
    }

    public RowBounds getPageBounds() {
        return new RowBounds(getCurrentPage(), getPageSize());
    }

    private String getOrder() {
        return getSorter().replaceAll("_", " ");
    }
}
