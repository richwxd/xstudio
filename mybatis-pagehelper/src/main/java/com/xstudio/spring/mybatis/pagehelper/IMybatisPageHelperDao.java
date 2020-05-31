package com.xstudio.spring.mybatis.pagehelper;

import com.github.pagehelper.Page;
import com.xstudio.core.service.IAbstractDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基础dao
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
public interface IMybatisPageHelperDao<T, K> extends IAbstractDao<T, K, PageBounds, Page<T>> {
    /**
     * 统计总数
     *
     * @param record 统计条件
     * @return 统计结果
     */
    @Override
    Long countByExample(@Param("example") T record);

    /**
     * 主键获取
     *
     * @param key 主键
     * @return T
     */
    @Override
    T selectByPrimaryKey(@Param("key") K key);

    /**
     * 主键删除
     *
     * @param key 主键
     * @return 删除的数量
     */
    @Override
    int deleteByPrimaryKey(@Param("key") K key);

    /**
     * 批量删除
     *
     * @param keys 主键
     * @return 删除条数
     */
    @Override
    int batchDeleteByPrimaryKey(@Param("items") List<K> keys);

    /**
     * 按条件更新选定的值
     *
     * @param example 条件
     * @param record  值对象
     * @return 更新的条数
     */
    @Override
    int updateByExampleSelective(@Param("example") T example, @Param("record") T record);

    /**
     * 按条件获取（不含大字段）
     *
     * @param record     条件
     * @param pageBounds 分页参数
     * @return 对象list
     */
    @Override
    Page<T> selectByExample(@Param("example") T record, @Param("pageBounds") PageBounds pageBounds);

    /**
     * 按条件获取（含大字段）
     *
     * @param record     条件
     * @param pageBounds 分页参数
     * @return 对象list
     */
    @Override
    Page<T> selectByExampleWithBLOBs(@Param("example") T record, @Param("pageBounds") PageBounds pageBounds);

    /**
     * 分页模糊搜索
     *
     * @param record     对象
     * @param pageBounds 分页参数
     * @return 对象list
     */
    @Override
    Page<T> fuzzySearchByPager(@Param("example") T record, @Param("pageBounds") PageBounds pageBounds);
}
