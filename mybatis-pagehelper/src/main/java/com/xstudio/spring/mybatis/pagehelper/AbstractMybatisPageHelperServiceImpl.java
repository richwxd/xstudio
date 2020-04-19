package com.xstudio.spring.mybatis.pagehelper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xstudio.antdesign.Pagination;
import com.xstudio.core.BaseModelObject;
import com.xstudio.core.ErrorConstant;
import com.xstudio.core.Msg;
import com.xstudio.core.service.AbstractServiceImpl;
import com.xstudio.spring.mybatis.antdesign.PageResponse;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础服务实现
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
public abstract class AbstractMybatisPageHelperServiceImpl<T extends BaseModelObject<K>, K> extends AbstractServiceImpl<T, K, PageBounds, PageResponse<T>, List<T>> implements IMybatisPageHelperService<T, K> {

    @Override
    public Msg<PageResponse<T>> fuzzySearch(T record) {
        Msg<PageResponse<T>> msg = new Msg<>();
        Msg<PageResponse<T>> pageListMsg = new Msg<>();
        List<T> list = new ArrayList<>();
        int limit = 5000;
        int offset = 0;
        PageBounds pageBounds = new PageBounds(offset, limit);
        boolean doLoop = true;
        while (Boolean.TRUE.equals(pageListMsg.isSuccess()) && doLoop) {
            pageListMsg = fuzzySearchByPager(record, pageBounds);
            if (Boolean.TRUE.equals(pageListMsg.isSuccess())) {
                doLoop = pageListMsg.getData().size() >= limit;
                list.addAll(pageListMsg.getData());
            }
            offset = offset + limit;
            pageBounds = new PageBounds(offset, limit);
        }

        if (list.isEmpty()) {
            msg.setResult(ErrorConstant.NO_MATCH, ErrorConstant.NO_MATCH_MSG);
            return msg;
        }
        PageResponse<T> pageList = new PageResponse<>(list, pageBounds.getTotal(), 1);
        msg.setData(pageList);
        return msg;
    }

    @Override
    public Msg<PageResponse<T>> fuzzySearchByPager(T record, PageBounds pageBounds) {
        Msg<PageResponse<T>> msg = new Msg<>();
        PageHelper.startPage(pageBounds.getPage(), pageBounds.getLimit());
        PageHelper.orderBy(pageBounds.getOrders());
        Page<T> result = (Page<T>) getRepositoryDao().fuzzySearch(record);
        if (result.isEmpty()) {
            msg.setResult(ErrorConstant.NO_MATCH, ErrorConstant.NO_MATCH_MSG);
            return msg;
        }
        PageResponse<T> pageList = new PageResponse<>(result, pageBounds.getTotal(), 1);
        msg.setData(pageList);

        return msg;
    }

    @Override
    public Msg<PageResponse<T>> selectAllByExample(T record) {
        List<String> orders = new ArrayList<>();
        orders.add("create_at desc");
        return selectAllByExample(record, orders);
    }


    @Override
    public Msg<PageResponse<T>> selectAllByExample(T record, List<?> orders) {
        Msg<PageResponse<T>> msg = new Msg<>();
        Msg<PageResponse<T>> pageListMsg = new Msg<>();
        List<T> list = new ArrayList<>();
        int limit = 5000;
        PageBounds pageBounds = new PageBounds(1, limit);

        pageBounds.setOrders(getOrder(orders));
        boolean doLoop = true;
        Pagination pagination = new Pagination();
        while (Boolean.TRUE.equals(pageListMsg.isSuccess()) && doLoop) {
            pageListMsg = selectByExampleByPager(record, pageBounds);
            if (Boolean.TRUE.equals(pageListMsg.isSuccess())) {
                doLoop = pageListMsg.getData().size() >= limit;
                list.addAll(pageListMsg.getData());
            }
            pagination = pageListMsg.getData().getPagination();
            pageBounds.setPage(pageBounds.getPage() + 1);
        }

        if (list.isEmpty()) {
            msg.setResult(ErrorConstant.NO_MATCH, ErrorConstant.NO_MATCH_MSG);
            return msg;
        }
        PageResponse<T> pageList = new PageResponse<>(list, pagination.getTotal(), 1);
        msg.setData(pageList);
        return msg;
    }

    private String getOrder(List<?> orders) {
        StringBuilder sb = new StringBuilder();
        for (Object order : orders) {
            sb.append(order);
            sb.append(",");
        }
        sb.reverse();
        return sb.toString();
    }

    @Override
    public Msg<PageResponse<T>> selectByExampleByPager(T record, PageBounds pageBounds) {
        Msg<PageResponse<T>> msg = new Msg<>();
        PageHelper.startPage(pageBounds.getPage(), pageBounds.getLimit());
        PageHelper.orderBy(pageBounds.getOrders());

        Page<T> result = (Page<T>) getRepositoryDao().selectByExample(record, pageBounds);
        if (result.isEmpty()) {
            msg.setResult(ErrorConstant.NO_MATCH, ErrorConstant.NO_MATCH_MSG);
            return msg;
        }

        PageResponse<T> pageList = new PageResponse<>(result, pageBounds.getTotal(), pageBounds.getPage());
        msg.setData(pageList);
        return msg;
    }

    @Override
    public Msg<T> selectOneByExample(T record) {
        List<?> orders = new ArrayList<>();
        return selectOneByExample(record, orders);
    }

    @Override
    public Msg<T> selectOneByExample(T record, List<?> orders) {
        Msg<T> msg = new Msg<>();
        Page<T> result;
        PageBounds pageBounds = new PageBounds(0, 2);
        pageBounds.setOrders(getOrder(orders));

        try {
            result = (Page<T>) getRepositoryDao().selectByExampleWithBLOBs(record, pageBounds);
        } catch (Exception e) {
            result = (Page<T>) getRepositoryDao().selectByExample(record, pageBounds);
        }
        if (null == result || result.isEmpty()) {
            msg.setResult(ErrorConstant.NO_MATCH, ErrorConstant.NO_MATCH_MSG);
            return msg;
        }

        if (result.size() > 1) {
            msg.setResult(ErrorConstant.CONFLICT, ErrorConstant.CONFLICT_MSG);
            return msg;
        }

        msg.setData(result.get(0));
        return msg;
    }

    @Override
    public Msg<T> selectOneByExampleWithBlobs(T record, List<?> orders) {
        Msg<T> msg = new Msg<>();

        PageBounds pageBounds = new PageBounds(0, 2);
        pageBounds.setOrders(getOrder(orders));

        Page<T> result = (Page<T>) getRepositoryDao().selectByExampleWithBLOBs(record, pageBounds);

        if (CollectionUtils.isEmpty(result)) {
            msg.setResult(ErrorConstant.NO_MATCH, ErrorConstant.NO_MATCH_MSG);
            return msg;
        }

        if (result.size() > 1) {
            msg.setResult(ErrorConstant.CONFLICT, ErrorConstant.CONFLICT_MSG);
            return msg;
        }

        msg.setData(result.get(0));
        return msg;
    }


    @Override
    public Msg<PageResponse<T>> selectByExampleWithBlobsByPager(T record, PageBounds pageBounds) {
        Msg<PageResponse<T>> msg = new Msg<>();
        PageHelper.startPage(pageBounds.getPage(), pageBounds.getLimit());
        PageHelper.orderBy(pageBounds.getOrders());

        Page<T> result = (Page<T>) getRepositoryDao().selectByExampleWithBLOBs(record, pageBounds);
        if (result.isEmpty()) {
            msg.setResult(ErrorConstant.NO_MATCH, ErrorConstant.NO_MATCH_MSG);
            return msg;
        }
        PageResponse<T> pageList = new PageResponse<>(result, pageBounds.getTotal(), pageBounds.getPage());
        msg.setData(pageList);
        return msg;
    }

    @Override
    public Msg<String> uniqueValid(T record) {
        Msg<String> msg = new Msg<>();
        Object key = record.valueOfKey();
        if (null == key) {
            Long existNumber = getRepositoryDao().countByExample(record);
            if (existNumber > 0) {
                msg.setResult(ErrorConstant.CONFLICT, ErrorConstant.CONFLICT_MSG);
                return msg;
            }

            return msg;
        }

        /* 主键存在 参数获取的对象主键不一致时 返回错误 */
        record.assignKeyValue(null);

        PageBounds pageBounds = new PageBounds(0, 2);

        Page<T> dbRecord = (Page<T>) getRepositoryDao().selectByExample(record, pageBounds);
        if (null != dbRecord && !dbRecord.isEmpty() && !key.equals(dbRecord.get(0).valueOfKey())) {
            msg.setResult(ErrorConstant.CONFLICT, ErrorConstant.CONFLICT_MSG);
            return msg;
        }

        return msg;
    }
}
