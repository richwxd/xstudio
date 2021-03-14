package io.github.xbeeant.spring.mybatis.antdesign;

import io.github.xbeeant.antdesign.TableRequest;
import io.github.xbeeant.spring.mybatis.pagehelper.PageBounds;

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

    public PageBounds getPageBounds() {
        PageBounds pageBounds = new PageBounds(getCurrentPage(), getPageSize());
        pageBounds.setOrders(getOrder());
        return pageBounds;
    }

    private String getOrder() {
        return getSorter().replaceAll("_", " ");
    }
}
