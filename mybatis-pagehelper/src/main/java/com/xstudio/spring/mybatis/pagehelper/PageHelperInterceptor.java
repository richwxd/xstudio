package com.xstudio.spring.mybatis.pagehelper;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;

import java.util.Properties;

/**
 * pagehelper 分页插件配置
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
public class PageHelperInterceptor {
    public static Interceptor getInterceptor() {
        // 分页 插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties props = new Properties();
        props.setProperty("dialectClass", "com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect");
        pageInterceptor.setProperties(props);
        return pageInterceptor;
    }
}
