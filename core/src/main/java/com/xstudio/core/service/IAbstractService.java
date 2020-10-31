package com.xstudio.core.service;

import com.xstudio.core.ApiResponse;

import java.util.List;

/**
 * @author xiaobiao
 * @version 2020/2/2
 */
public interface IAbstractService<T, K, P, L extends List<T>, D extends List<T>> {
    /**
     * 批量删除
     *
     * @param keys 主键
     * @return 删除条数
     */
    ApiResponse<Integer> batchDeleteByPrimaryKey(List<K> keys);

    /**
     * 批量插入
     *
     * @param record 对象
     * @return 插入成功数
     */
    ApiResponse<L> batchInsert(L record);

    /**
     * 批量按主键更新设定的值
     *
     * @param record 值
     * @return 更新的条数
     */
    ApiResponse<L> batchUpdateByPrimaryKeySelective(L record);

    /**
     * 统计总数
     *
     * @param example 统计条件
     * @return 统计结果
     */
    Long countByExample(T example);

    /**
     * 按条件删除
     *
     * @param example 条件对象
     * @return {@link Integer}
     */
    ApiResponse<Integer> deleteByExample(T example);

    /**
     * 主键删除
     *
     * @param keys 主键
     * @return 删除条数
     */
    ApiResponse<Integer> deleteByPrimaryKey(K keys);

    /**
     * 模糊搜索
     *
     * @param example 搜索对象
     * @return 满足条件的对象
     */
    ApiResponse<L> fuzzySearch(T example);

    /**
     * 分页模糊搜索
     *
     * @param example    对象
     * @param pageBounds 分页参数
     * @return 对象list
     */
    ApiResponse<L> fuzzySearchByPager(T example, P pageBounds);

    /**
     * 获取dao实现
     *
     * @return Dao实现
     */
    IAbstractDao<T, K, P, D> getRepositoryDao();

    /**
     * 全字段写入
     *
     * @param record 对象
     * @return 对象
     */
    ApiResponse<T> insert(T record);

    /**
     * 插入设置的值
     *
     * @param record 对象
     * @return 插入成功数
     */
    ApiResponse<T> insertSelective(T record);

    /**
     * 按条件获取（含大字段）
     *
     * @param example 条件
     * @return 对象list
     */
    ApiResponse<L> selectAllByExample(T example);

    /**
     * 按条件获取（含大字段），指定排序
     *
     * @param example 条件
     * @param orders  排序
     * @return 对象list
     */
    ApiResponse<L> selectAllByExample(T example, List<?> orders);

    /**
     * 分页获取
     *
     * @param example    条件
     * @param pageBounds 分页参数
     * @return 对象list
     */
    ApiResponse<L> selectByExampleByPager(T example, P pageBounds);

    /**
     * 分页获取(含大字段）
     *
     * @param example    条件
     * @param pageBounds 分页参数
     * @return 对象list
     */
    ApiResponse<L> selectByExampleWithBlobsByPager(T example, P pageBounds);

    /**
     * 按主键获取
     *
     * @param keys 主键
     * @return 对象
     */
    ApiResponse<T> selectByPrimaryKey(K keys);

    /**
     * 按条件获取（不含大字段）
     *
     * @param example 条件
     * @param orders  排序
     * @return 对象list
     */
    ApiResponse<T> selectOneByExample(T example, List<?> orders);

    /**
     * 按条件获取（不含大字段） distinct = true
     *
     * @param example 条件
     * @return 对象list
     */
    ApiResponse<T> selectOneByExample(T example);

    /**
     * 按条件获取（含大字段）
     *
     * @param example 条件
     * @param orders  排序
     * @return 对象list
     */
    ApiResponse<T> selectOneByExampleWithBlobs(T example, List<?> orders);

    /**
     * 唯一验证
     *
     * @param record 待验证对象字段
     * @return 验证结果 msg.code == 0 时表示该字段可用
     */
    ApiResponse<String> uniqueValid(T record);

    /**
     * 全字段按条件更新
     *
     * @param record  赋值对象
     * @param example 更新条件
     * @return 对象
     */
    ApiResponse<T> updateByExample(T record, T example);

    /**
     * 按条件更新选定的值
     *
     * @param record  值对象
     * @param example 条件
     * @return 更新的条数
     */
    ApiResponse<T> updateByExampleSelective(T record, T example);

    /**
     * 全字段按主键更新
     *
     * @param record 赋值对象
     * @return 对象
     */
    ApiResponse<T> updateByPrimaryKey(T record);

    /**
     * 更新选定的值
     *
     * @param record 对象
     * @return 更新的条数
     */
    ApiResponse<T> updateByPrimaryKeySelective(T record);
}

