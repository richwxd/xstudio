package com.xstudio.spring.mybatis.rest;

import com.xstudio.antdesign.TableResponse;
import com.xstudio.core.BaseModelObject;
import com.xstudio.core.ErrorConstant;
import com.xstudio.core.Msg;
import com.xstudio.spring.mybatis.antdesign.PageRequest;
import com.xstudio.spring.web.rest.AbstractBaseRestController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2020/2/3
 */
public abstract class AbstractRestController<Target extends BaseModelObject<Key>, Key> extends AbstractBaseRestController<Target, Key> {

    @GetMapping(value = {"table"})
    public Msg<TableResponse<Target>> table(Target object, PageRequest pageRequest, HttpServletRequest request, HttpServletResponse response) {
        Msg<TableResponse<Target>> msg = new Msg<>();
        PageRequest webRequest = new PageRequest(pageRequest);

        Msg<List<Target>> list = getService().fuzzySearchByPager(object, webRequest.getPageBounds());
        if (!list.isSuccess()) {
            msg.setResult(ErrorConstant.NO_MATCH, "没有匹配项");
            return msg;
        }

        TableResponse<Target> pageResponse = new TableResponse<>();
        pageResponse.setList(list.getData());
        msg.setData(pageResponse);
        return msg;
    }
}
