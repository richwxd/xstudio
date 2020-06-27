package com.xstudio.core.service;

import com.xstudio.core.BaseModelObject;
import com.xstudio.core.ErrorConstant;
import com.xstudio.core.Msg;
import com.xstudio.core.date.DateTime;
import org.apache.commons.collections4.ListUtils;

import java.util.List;

/**
 * 基础服务
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
public abstract class AbstractServiceImpl<T extends BaseModelObject<K>, K, P, L extends List<T>, M extends List<T>> implements IAbstractService<T, K, P, L, M> {
    public static int batchPerSqlNumber = 100;

    @Override
    public Long countByExample(T record) {
        return getRepositoryDao().countByExample(record);
    }


    @Override
    public Msg<T> insertSelective(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setCreateInfo(record);
        int result = getRepositoryDao().insertSelective(record);
        boolean failed = isFailed(msg, 0 == result, ErrorConstant.INSERT_NONE, ErrorConstant.INSERT_NONE_MSG);
        if (failed) {
            return msg;
        }

        msg.setData(record);
        return msg;
    }

    /**
     * 是否数据库操作失败
     *
     * @param msg       返回消息对象
     * @param isFailed  是否失败标志位
     * @param errorCode 返回的错误码
     * @param message   返回的错误消息
     * @return 是否失败
     */
    private boolean isFailed(Msg<T> msg, boolean isFailed, int errorCode, String message) {
        if (isFailed) {
            msg.setCode(errorCode);
            msg.setMsg(message);
            return true;
        }
        return false;
    }

    @Override
    public Msg<L> batchInsertSelective(L records) {
        Msg<L> msg = new Msg<>();
        if (null == records || records.isEmpty()) {
            msg.setResult(ErrorConstant.INSERT_NONE, ErrorConstant.INSERT_NONE_MSG);
            return msg;
        }
        for (T record : records) {
            setDefaults(record);
            setCreateInfo(record);
        }

        int insertCount = 0;
        List<List<T>> lists = ListUtils.partition(records, batchPerSqlNumber);
        for (List<T> list : lists) {
            insertCount = insertCount + getRepositoryDao().batchInsertSelective(list);
        }

        if (0 == insertCount) {
            msg.setResult(ErrorConstant.UPDATE_NONE, ErrorConstant.UPDATE_NONE_MSG);
            return msg;
        }
        msg.setData(records);

        return msg;
    }

    @Override
    public Msg<Integer> deleteByPrimaryKey(K keys) {
        Msg<Integer> msg = new Msg<>();
        int result = getRepositoryDao().deleteByPrimaryKey(keys);
        if (0 == result) {
            msg.setResult(ErrorConstant.DELETE_NONE, ErrorConstant.DELETE_NONE_MSG);
            return msg;
        }

        return msg;
    }

    @Override
    public Msg<Integer> batchDeleteByPrimaryKey(List<K> keys) {
        Msg<Integer> msg = new Msg<>();
        if (null == keys || keys.isEmpty()) {
            msg.setResult(ErrorConstant.NONEMPTY_PARAM, ErrorConstant.NONEMPTY_PARAM_MSG);
            return msg;
        }
        int result = getRepositoryDao().batchDeleteByPrimaryKey(keys);
        if (0 == result) {
            msg.setResult(ErrorConstant.DELETE_NONE, ErrorConstant.DELETE_NONE_MSG);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<T> updateByPrimaryKeySelective(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByPrimaryKeySelective(record);
        if (isFailed(msg, 0 == result, ErrorConstant.UPDATE_NONE, ErrorConstant.UPDATE_NONE_MSG)) {
            return msg;
        }

        // 重新获取数据
        T dbRecord = getRepositoryDao().selectByPrimaryKey(record.valueOfKey());
        msg.setData(dbRecord);
        return msg;
    }

    @Override
    public Msg<T> updateByExampleSelective(T example, T record) {
        Msg<T> msg = new Msg<>();
        K keyValue = record.valueOfKey();
        setDefaults(record);
        setUpdateInfo(record);
        record.assignKeyValue(null);
        int result = getRepositoryDao().updateByExampleSelective(example, record);
        if (isFailed(msg, 0 == result, ErrorConstant.UPDATE_NONE, ErrorConstant.UPDATE_NONE_MSG)) {
            return msg;
        }

        // 重新获取数据
        if (null != keyValue) {
            T dbRecord = getRepositoryDao().selectByPrimaryKey(keyValue);
            msg.setData(dbRecord);
        }
        return msg;
    }

    @Override
    public Msg<L> batchUpdateByPrimaryKeySelective(L records) {
        Msg<L> msg = new Msg<>();
        if (null == records || records.isEmpty()) {
            msg.setResult(ErrorConstant.NONEMPTY_PARAM, ErrorConstant.NONEMPTY_PARAM_MSG);
            return msg;
        }
        for (T record : records) {
            setDefaults(record);
            setUpdateInfo(record);
        }

        int count = 0;
        List<List<T>> lists = ListUtils.partition(records, batchPerSqlNumber);
        for (List<T> list : lists) {
            count = count + getRepositoryDao().batchUpdateByPrimaryKeySelective(list);
        }

        if (0 == count) {
            msg.setResult(ErrorConstant.UPDATE_NONE, ErrorConstant.UPDATE_NONE_MSG);
            return msg;
        }

        msg.setData(records);
        return msg;
    }

    @Override
    public Msg<T> selectByPrimaryKey(K keys) {
        Msg<T> msg = new Msg<>();
        T result = getRepositoryDao().selectByPrimaryKey(keys);
        if (isFailed(msg, null == result, ErrorConstant.NO_MATCH, ErrorConstant.NO_MATCH_MSG)) {
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    /**
     * 获取dao服务
     *
     * @return {@link IAbstractDao}
     */
    @Override
    public abstract IAbstractDao<T, K, P, M> getRepositoryDao();

    /**
     * 设置对象insert update时的默认值
     *
     * @param record 对象
     */
    @Override
    public abstract void setDefaults(T record);

    @Override
    public void setCreateInfo(T record) {
        DateTime now = new DateTime();
        if (null == record.getCreateAt()) {
            record.setCreateAt(now);
        }
        record.setUpdateAt(now);
        if (null == record.getCreateBy()) {
            record.setCreateBy(getActorId(record));
        }

        if (null == record.getUpdateBy()) {
            record.setUpdateBy(getActorId(record));
        }
    }

    @Override
    public void setUpdateInfo(T record) {
        record.setUpdateAt(new DateTime());
        record.setUpdateBy(getActorId(record));
    }

    /**
     * 获取系统当前操作的用户的ID
     *
     * @param record 对象
     * @return 用户ID字符串
     */
    public abstract String getActorId(T record);
}
