package io.github.xbeeant.spring.mybatis.pagehelper;

import com.github.pagehelper.Page;
import io.github.xbeeant.core.BaseModelObject;
import io.github.xbeeant.core.service.IAbstractService;
import io.github.xbeeant.spring.mybatis.antdesign.PageResponse;

/**
 * 基础服务
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
public interface IMybatisPageHelperService<T extends BaseModelObject<K>, K> extends IAbstractService<T, K, PageBounds, PageResponse<T>, Page<T>> {
}
