package com.xstudio.spring.mybatis.rest;

import com.xstudio.antdesign.TableResponse;
import com.xstudio.core.ApiResponse;
import com.xstudio.core.BaseModelObject;
import com.xstudio.core.ErrorCodeConstant;
import com.xstudio.spring.mybatis.antdesign.PageRequest;
import com.xstudio.spring.web.rest.AbstractBaseRestController;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2020/2/3
 */
public abstract class AbstractRestController<T extends BaseModelObject<K>, K, P extends RowBounds, L extends List<T>, D extends List<T>> extends AbstractBaseRestController<T, K, P, L, D> {

    @GetMapping(value = {"table"})
    public ApiResponse<TableResponse<T>> table(T object, PageRequest pageRequest, HttpServletRequest request, HttpServletResponse response) {
        ApiResponse<TableResponse<T>> apiResponse = new ApiResponse<>();
        PageRequest webRequest = new PageRequest(pageRequest);

        ApiResponse<L> list = getService().fuzzySearchByPager(object, (P) webRequest.getPageBounds());
        if (!list.isSuccess()) {
            apiResponse.setResult(ErrorCodeConstant.NO_MATCH, "没有匹配项");
            return apiResponse;
        }

        TableResponse<T> pageResponse = new TableResponse<>();
        pageResponse.setList(list.getData());
        apiResponse.setData(pageResponse);
        return apiResponse;
    }
}
