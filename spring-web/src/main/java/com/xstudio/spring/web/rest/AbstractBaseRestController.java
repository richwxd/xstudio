package com.xstudio.spring.web.rest;

import com.xstudio.core.ApiResponse;
import com.xstudio.core.BaseModelObject;
import com.xstudio.core.service.IAbstractService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2020/2/3
 */
public abstract class AbstractBaseRestController<T extends BaseModelObject<K>, K, P, L extends List<T>, D extends List<T>> {

    /**
     * 通过主键删除记录
     *
     * @param id       主键
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ApiResponse&lt;Target&gt;
     */
    @ApiOperation(value = "删除", notes = "")
    @DeleteMapping(value = "/{id}")
    public ApiResponse<Integer> delete(@ApiParam(value = "id", required = true, example = "") @PathVariable(name = "id") K id,
                                       HttpServletRequest request, HttpServletResponse response) {
        return getService().deleteByPrimaryKey(id);
    }

    /**
     * 获取服务
     *
     * @return 抽象IAbstractService
     */
    public abstract IAbstractService<T, K, P, L, D> getService();

    /**
     * 通过组件获取详情
     *
     * @param id       主键
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ApiResponse&lt;Target&gt;
     */
    @ApiOperation(value = "获取详情", notes = "")
    @GetMapping(value = "/{id}")
    public ApiResponse<T> getDetails(@ApiParam(value = "id", required = true, example = "")
                                     @PathVariable(name = "id") K id,
                                     HttpServletRequest request, HttpServletResponse response) {
        return getService().selectByPrimaryKey(id);
    }

    /**
     * 写入一条数据
     *
     * @param record   记录 {@link T}
     * @param request  请求 {@link HttpServletRequest}
     * @param response 响应 {@link HttpServletResponse}
     * @return {@link ApiResponse}
     * @see ApiResponse
     * @see T
     */
    @ApiOperation(value = "新增", notes = "")
    @PostMapping(value = "")
    public ApiResponse<T> post(@RequestBody T record,
                               HttpServletRequest request, HttpServletResponse response) {
        return getService().insertSelective(record);
    }

    /**
     * 更新一条数据
     *
     * @param record   对象
     * @param id       主键
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ApiResponse&lt;Target&gt;
     */
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "更新", notes = "")
    public ApiResponse<T> put(@RequestBody T record, @PathVariable(name = "id") K id, HttpServletRequest request, HttpServletResponse response) {
        record.assignKeyValue(id);
        return getService().updateByPrimaryKeySelective(record);
    }

    /**
     * 唯一性校验
     *
     * @param object  对象
     * @param request HttpServletRequest
     * @return ApiResponse&lt;Target&gt;
     */
    @PostMapping(value = "/validate")
    @ApiOperation(value = "唯一性校验", notes = "")
    public ApiResponse<String> validate(@RequestBody T object, HttpServletRequest request) {
        return getService().uniqueValid(object);
    }
}
