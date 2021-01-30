package com.xstudio.spring.mybatis.rest;

import com.github.pagehelper.Page;
import com.xstudio.antdesign.TableResponse;
import com.xstudio.core.ApiResponse;
import com.xstudio.core.BaseModelObject;
import com.xstudio.core.ErrorCodeConstant;
import com.xstudio.spring.mybatis.antdesign.PageRequest;
import com.xstudio.spring.mybatis.antdesign.PageResponse;
import com.xstudio.spring.mybatis.pagehelper.PageBounds;
import com.xstudio.spring.web.rest.AbstractBaseRestController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaobiao
 * @version 2020/2/3
 */
public abstract class AbstractRestController<T extends BaseModelObject<K>, K> extends AbstractBaseRestController<T, K, PageBounds, PageResponse<T>, Page<T>> {

    @GetMapping(value = {"table"})
    public ApiResponse<TableResponse<T>> table(T object, PageRequest pageRequest, HttpServletRequest request, HttpServletResponse response) {
        ApiResponse<TableResponse<T>> apiResponse = new ApiResponse<>();
        PageRequest webRequest = new PageRequest(pageRequest);

        ApiResponse<PageResponse<T>> list = getService().fuzzySearchByPager(object, webRequest.getPageBounds());
        if (!list.getSuccess()) {
            apiResponse.setResult(ErrorCodeConstant.NO_MATCH, ErrorCodeConstant.NO_MATCH_MSG);
            return apiResponse;
        }

        TableResponse<T> pageResponse = new TableResponse<>();
        pageResponse.setList(list.getData());
        pageResponse.setPagination(list.getData().getPagination());
        apiResponse.setData(pageResponse);
        return apiResponse;
    }
}
