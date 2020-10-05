package com.xstudio.core.service;

import com.xstudio.core.ApiResponse;
import com.xstudio.core.BaseModelObject;
import com.xstudio.core.ErrorCodeConstant;
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
    public static int batchPerSqlNumber = 1000;

    public boolean isWithBlobs() {
        return false;
    }

    @Override
    public Long countByExample(T example) {
        return getRepositoryDao().countByExample(example);
    }


    /**
     * 是否数据库操作失败
     *
     * @param apiResponse 返回消息对象
     * @param isFailed    是否失败标志位
     * @param errorCode   返回的错误码
     * @param message     返回的错误消息
     * @return 是否失败
     */
    private boolean isFailed(ApiResponse<T> apiResponse, boolean isFailed, int errorCode, String message) {
        if (isFailed) {
            apiResponse.setCode(errorCode);
            apiResponse.setMsg(message);
            return true;
        }
        return false;
    }


    @Override
    public ApiResponse<T> updateByPrimaryKeySelective(T record) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByPrimaryKeySelective(record);
        if (isFailed(apiResponse, 0 == result, ErrorCodeConstant.UPDATE_NONE, ErrorCodeConstant.UPDATE_NONE_MSG)) {
            return apiResponse;
        }

        // 重新获取数据
        T dbRecord = getRepositoryDao().selectByPrimaryKey(record.getKey());
        apiResponse.setData(dbRecord);
        return apiResponse;
    }


    @Override
    public ApiResponse<T> selectByPrimaryKey(K keys) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        T result = getRepositoryDao().selectByPrimaryKey(keys);
        if (isFailed(apiResponse, null == result, ErrorCodeConstant.NO_MATCH, ErrorCodeConstant.NO_MATCH_MSG)) {
            return apiResponse;
        }

        apiResponse.setData(result);
        return apiResponse;
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
    public abstract void setDefaults(T record);

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

    @Override
    public ApiResponse<Integer> deleteByPrimaryKey(K keys) {
        ApiResponse<Integer> apiResponse = new ApiResponse<>();
        int result = getRepositoryDao().deleteByPrimaryKey(keys);
        if (0 == result) {
            apiResponse.setResult(ErrorCodeConstant.DELETE_NONE, ErrorCodeConstant.DELETE_NONE_MSG);
            return apiResponse;
        }

        return apiResponse;
    }

    @Override
    public ApiResponse<Integer> batchDeleteByPrimaryKey(List<K> keys) {
        ApiResponse<Integer> apiResponse = new ApiResponse<>();
        if (null == keys || keys.isEmpty()) {
            apiResponse.setResult(ErrorCodeConstant.NONEMPTY_PARAM, ErrorCodeConstant.NONEMPTY_PARAM_MSG);
            return apiResponse;
        }
        int result = getRepositoryDao().batchDeleteByPrimaryKey(keys);
        if (0 == result) {
            apiResponse.setResult(ErrorCodeConstant.DELETE_NONE, ErrorCodeConstant.DELETE_NONE_MSG);
            return apiResponse;
        }

        apiResponse.setData(result);
        return apiResponse;
    }

    @Override
    public ApiResponse<Integer> deleteByExample(T example) {
        ApiResponse<Integer> result = new ApiResponse<>();
        Integer integer = getRepositoryDao().deleteByExample(example);
        if (0 == integer) {
            result.setResult(ErrorCodeConstant.DELETE_NONE, ErrorCodeConstant.DELETE_NONE_MSG);
            return result;
        }

        result.setData(integer);
        return result;
    }

    @Override
    public ApiResponse<T> insert(T record) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        setDefaults(record);
        setCreateInfo(record);
        int result = getRepositoryDao().insert(record);
        boolean failed = isFailed(apiResponse, 0 == result, ErrorCodeConstant.INSERT_NONE, ErrorCodeConstant.INSERT_NONE_MSG);
        if (failed) {
            return apiResponse;
        }

        apiResponse.setData(record);
        return apiResponse;
    }

    @Override
    public ApiResponse<T> insertSelective(T record) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        setDefaults(record);
        setCreateInfo(record);
        int result = getRepositoryDao().insertSelective(record);
        boolean failed = isFailed(apiResponse, 0 == result, ErrorCodeConstant.INSERT_NONE, ErrorCodeConstant.INSERT_NONE_MSG);
        if (failed) {
            return apiResponse;
        }

        apiResponse.setData(record);
        return apiResponse;
    }

    @Override
    public ApiResponse<L> batchInsertSelective(L records) {
        ApiResponse<L> apiResponse = new ApiResponse<>();
        if (null == records || records.isEmpty()) {
            apiResponse.setResult(ErrorCodeConstant.INSERT_NONE, ErrorCodeConstant.INSERT_NONE_MSG);
            return apiResponse;
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
            apiResponse.setResult(ErrorCodeConstant.UPDATE_NONE, ErrorCodeConstant.UPDATE_NONE_MSG);
            return apiResponse;
        }
        apiResponse.setData(records);

        return apiResponse;
    }

    @Override
    public ApiResponse<T> updateByExample(T record, T example) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        K keyValue = record.getKey();
        setDefaults(record);
        setUpdateInfo(record);
        record.assignKeyValue(null);
        int result;
        if (isWithBlobs()) {
            result = getRepositoryDao().updateByExampleWithBLOBs(record, example);
        } else {
            result = getRepositoryDao().updateByExample(record, example);
        }
        if (isFailed(apiResponse, 0 == result, ErrorCodeConstant.UPDATE_NONE, ErrorCodeConstant.UPDATE_NONE_MSG)) {
            return apiResponse;
        }

        // 重新获取数据
        if (null != keyValue) {
            T dbRecord = getRepositoryDao().selectByPrimaryKey(keyValue);
            apiResponse.setData(dbRecord);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse<T> updateByPrimaryKey(T record) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        K keyValue = record.getKey();
        setDefaults(record);
        setUpdateInfo(record);

        int result;
        if (isWithBlobs()) {
            result = getRepositoryDao().updateByPrimaryKeyWithBLOBs(record);
        } else {
            result = getRepositoryDao().updateByPrimaryKey(record);
        }
        if (isFailed(apiResponse, 0 == result, ErrorCodeConstant.UPDATE_NONE, ErrorCodeConstant.UPDATE_NONE_MSG)) {
            return apiResponse;
        }

        // 重新获取数据
        if (null != keyValue) {
            T dbRecord = getRepositoryDao().selectByPrimaryKey(keyValue);
            apiResponse.setData(dbRecord);
        }
        return apiResponse;
    }


    @Override
    public ApiResponse<T> updateByExampleSelective(T example, T record) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        K keyValue = record.getKey();
        setDefaults(record);
        setUpdateInfo(record);

        int result = getRepositoryDao().updateByExampleSelective(example, record);
        if (isFailed(apiResponse, 0 == result, ErrorCodeConstant.UPDATE_NONE, ErrorCodeConstant.UPDATE_NONE_MSG)) {
            return apiResponse;
        }

        // 重新获取数据
        if (null != keyValue) {
            T dbRecord = getRepositoryDao().selectByPrimaryKey(keyValue);
            apiResponse.setData(dbRecord);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse<L> batchUpdateByPrimaryKeySelective(L records) {
        ApiResponse<L> apiResponse = new ApiResponse<>();
        if (null == records || records.isEmpty()) {
            apiResponse.setResult(ErrorCodeConstant.NONEMPTY_PARAM, ErrorCodeConstant.NONEMPTY_PARAM_MSG);
            return apiResponse;
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
            apiResponse.setResult(ErrorCodeConstant.UPDATE_NONE, ErrorCodeConstant.UPDATE_NONE_MSG);
            return apiResponse;
        }

        apiResponse.setData(records);
        return apiResponse;
    }
}
