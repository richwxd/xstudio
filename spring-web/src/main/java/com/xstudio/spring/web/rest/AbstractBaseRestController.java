package com.xstudio.spring.web.rest;

import com.xstudio.core.ApiResponse;
import com.xstudio.core.BaseModelObject;
import com.xstudio.core.ErrorCodeConstant;
import com.xstudio.core.service.IAbstractService;
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
     * @return Msg&lt;Target&gt;
     */
    @GetMapping(value = "/{id}")
    public ApiResponse<T> getByPathVariable(@PathVariable(name = "id") K id, HttpServletRequest request, HttpServletResponse response) {
        return getService().selectByPrimaryKey(id);
    }

    /**
     * 通过组件获取详情
     *
     * @param object   对象主键字段必须赋值
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @GetMapping(value = "/get")
    public ApiResponse<T> get(T object, HttpServletRequest request, HttpServletResponse response) {
        if (null == object.valueOfKey() || "".equals(object.valueOfKey())) {
            return new ApiResponse<>(ErrorCodeConstant.NONEMPTY_PARAM, "含有不能为空的参数");
        }
        return getService().selectByPrimaryKey(object.valueOfKey());
    }

    /**
     * 通过主键删除记录
     *
     * @param id       主键
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @DeleteMapping(value = "/{id}")
    public ApiResponse<Integer> delete(@PathVariable(name = "id") K id, HttpServletRequest request, HttpServletResponse response) {
        return getService().deleteByPrimaryKey(id);
    }

    /**
     * 通过主键删除记录
     *
     * @param object 对象 主键字段必须给值或赋值key字段
     * @return Msg&lt;Integer&gt;
     */
    @PostMapping(value = "delete")
    public ApiResponse<Integer> delete(@RequestBody T object) {
        if (object.valueOfKey() == null || "".equals(object.valueOfKey())) {
            return new ApiResponse<>(ErrorCodeConstant.NONEMPTY_PARAM, "含有不能为空的参数");
        }
        return getService().deleteByPrimaryKey(object.valueOfKey());
    }

    /**
     * 写入一条数据
     *
     * @param record   写入对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @PostMapping(value = "")
    public ApiResponse<T> post(@RequestBody T record, HttpServletRequest request, HttpServletResponse response) {
        return getService().insertSelective(record);
    }

    /**
     * 写入一条数据
     *
     * @param record   写入对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @PostMapping(value = "add")
    public ApiResponse<T> add(@RequestBody T record, HttpServletRequest request, HttpServletResponse response) {
        return getService().insertSelective(record);
    }

    /**
     * 更新一条数据
     *
     * @param record   对象
     * @param id       主键
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @PutMapping(value = "/{id}")
    public ApiResponse<T> put(@RequestBody T record, @PathVariable(name = "id") K id, HttpServletRequest request, HttpServletResponse response) {
        record.assignKeyValue(id);
        return getService().updateByPrimaryKeySelective(record);
    }

    /**
     * 更新一条数据
     *
     * @param record   对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @PostMapping(value = "/update")
    public ApiResponse<T> update(@RequestBody T record, HttpServletRequest request, HttpServletResponse response) {
        return getService().updateByPrimaryKeySelective(record);
    }

    /**
     * 唯一性校验
     *
     * @param object  对象
     * @param request HttpServletRequest
     * @return Msg&lt;Target&gt;
     */
    @PostMapping(value = "/validate")
    public ApiResponse<String> validate(@RequestBody T object, HttpServletRequest request) {
        return getService().uniqueValid(object);
    }
}
