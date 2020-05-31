package com.xstudio.spring.web.rest;

import com.xstudio.core.BaseModelObject;
import com.xstudio.core.ErrorConstant;
import com.xstudio.core.Msg;
import com.xstudio.core.service.IAbstractService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaobiao
 * @version 2020/2/3
 */
public abstract class AbstractBaseRestController <Target extends BaseModelObject<Key>, Key> {

    /**
     * 获取服务
     *
     * @return 抽象IAbstractService
     */
    public abstract IAbstractService getService();


    /**
     * 通过组件获取详情
     *
     * @param id       主键
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @GetMapping(value = "/{id}")
    public Msg<Target> get(@PathVariable(name = "id") Key id, HttpServletRequest request, HttpServletResponse response) {
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
    public Msg<Target> get(Target object, HttpServletRequest request, HttpServletResponse response) {
        if (null == object.valueOfKey() || "".equals(object.valueOfKey())) {
            return new Msg<>(ErrorConstant.NONEMPTY_PARAM,"含有不能为空的参数");
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
    public Msg<Boolean> delete(@PathVariable(name = "id") Key id, HttpServletRequest request, HttpServletResponse response) {
        return getService().deleteByPrimaryKey(id);
    }

    /**
     * 通过主键删除记录
     *
     * @param object 对象 主键字段必须给值或赋值key字段
     * @return Msg&lt;Integer&gt;
     */
    @PostMapping(value = "delete")
    public Msg<Integer> delete(@RequestBody Target object) {
        if (object.valueOfKey() == null || "".equals(object.valueOfKey())) {
            return new Msg<>(ErrorConstant.NONEMPTY_PARAM,"含有不能为空的参数");
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
    public Msg<Target> post(@RequestBody Target record, HttpServletRequest request, HttpServletResponse response) {
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
    public Msg<Target> add(@RequestBody Target record, HttpServletRequest request, HttpServletResponse response) {
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
    public Msg<Target> put(@RequestBody Target record, @PathVariable(name = "id") Key id, HttpServletRequest request, HttpServletResponse response) {
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
    public Msg<Target> update(@RequestBody Target record, HttpServletRequest request, HttpServletResponse response) {
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
    public Msg<Target> validate(@RequestBody Target object, HttpServletRequest request) {
        return getService().uniqueValid(object);
    }
}
