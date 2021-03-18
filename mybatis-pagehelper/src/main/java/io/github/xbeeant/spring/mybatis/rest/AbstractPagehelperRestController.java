package io.github.xbeeant.spring.mybatis.rest;

import com.github.pagehelper.Page;
import io.github.xbeeant.antdesign.TableResponse;
import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.core.BaseModelObject;
import io.github.xbeeant.core.ErrorCodeConstant;
import io.github.xbeeant.core.service.IAbstractService;
import io.github.xbeeant.spring.mybatis.antdesign.PageRequest;
import io.github.xbeeant.spring.mybatis.antdesign.PageResponse;
import io.github.xbeeant.spring.mybatis.pagehelper.PageBounds;
import io.github.xbeeant.spring.web.rest.AbstractRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Mybatis Pagehelper Rest接口
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
public abstract class AbstractPagehelperRestController<T extends BaseModelObject<K>, K> extends AbstractRestController<T, K, PageBounds, PageResponse<T>, Page<T>> {

    @Override
    public abstract IAbstractService<T, K, PageBounds, PageResponse<T>, Page<T>> getService();

    /**
     * 表格查询
     *
     * @param object      对象 {@link T}
     * @param pageRequest 分页参数 {@link PageRequest}
     * @param request     请求 {@link HttpServletRequest}
     * @param response    响应 {@link HttpServletResponse}
     * @return {@link ApiResponse}
     * @see ApiResponse
     * @see TableResponse
     */
    @ApiOperation(value = "表格查询", notes = "单表信息查询")
    @GetMapping(value = {"table"})
    public ApiResponse<TableResponse<T>> table(
            @ApiParam(value = "对象", required = true) T object,
            @ApiParam(value = "页面要求", required = true) PageRequest pageRequest,
            HttpServletRequest request, HttpServletResponse response) {
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
