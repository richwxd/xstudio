package com.xstudio.core.service;

import java.util.List;

/**
 * @author xiaobiao
 * @version 2020/2/2
 */
public interface IAbstractDao<T, K, P, M extends List<T>> {
    /**
     * 统计总数
     *
     * @param example 统计条件
     * @return 统计结果
     */
    Long countByExample(T example);

    /**
     * 插入设置的值
     *
     * @param record 对象
     * @return 插入成功数
     */
    int insertSelective(T record);

    /**
     * 批量插入设置的值
     *
     * @param record 对象
     * @return 插入成功数
     */
    int batchInsertSelective(List<T> record);

    /**
     * 主键删除
     *
     * @param key 主键
     * @return 删除条数
     */
    int deleteByPrimaryKey(K key);

    /**
     * 批量删除
     *
     * @param keys 主键
     * @return 删除条数
     */
    int batchDeleteByPrimaryKey(List<K> keys);

    /**
     * 更新选定的值
     *
     * @param record 对象
     * @return 更新的条数
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 按条件更新选定的值
     *
     * @param example 条件
     * @param record  值对象
     * @return 更新的条数
     */
    int updateByExampleSelective(T example, T record);


    /**
     * 全字段按条件更新（不含大字段）
     *
     * @param record  赋值对象
     * @param example 更新条件
     * @return 对象
     */
    Integer updateByExample(T record, T example);

    /**
     * 全字段按条件更新(含大字段）
     *
     * @param record  赋值对象
     * @param example 更新条件
     * @return 对象
     */
    Integer updateByExampleWithBLOBs(T record, T example);

    /**
     * 全字段按主键更新(不含大字段）
     *
     * @param record 赋值对象
     * @return 对象
     */
    Integer updateByPrimaryKey(T record);

    /**
     * 全字段按主键更新(含大字段）
     *
     * @param record 赋值对象
     * @return 对象
     */
    Integer updateByPrimaryKeyWithBLOBs(T record);

    /**
     * 批量按主键更新设定的值
     *
     * @param record 值
     * @return 更新的条数
     */
    int batchUpdateByPrimaryKeySelective(List<T> record);

    /**
     * 按主键获取
     *
     * @param key 主键
     * @return 对象
     */
    T selectByPrimaryKey(K key);

    /**
     * 按条件获取（不含大字段）
     *
     * @param exmaple    条件
     * @param pageBounds 分页、排序参数
     * @return 对象list
     */
    M selectByExample(T exmaple, P pageBounds);

    /**
     * 按条件获取（含大字段）
     *
     * @param example    条件
     * @param pageBounds 分页、排序参数
     * @return 对象list
     */
    M selectByExampleWithBLOBs(T example, P pageBounds);

    /**
     * 模糊搜索
     *
     * @param example 搜索对象
     * @return 满足条件的对象
     */
    M fuzzySearch(T example);

    /**
     * 分页模糊搜索
     *
     * @param example    对象
     * @param pageBounds 分页参数
     * @return 对象list
     */
    M fuzzySearchByPager(T example, P pageBounds);

    /**
     * 按条件删除
     *
     * @param example 条件对象
     * @return {@link Integer}
     */
    Integer deleteByExample(T example);

    /**
     * 全字段写入
     *
     * @param record 对象
     * @return 对象
     */
    Integer insert(T record);
}
