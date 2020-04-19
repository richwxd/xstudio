package com.xstudio.spring.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * sql session构造工厂操作类
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
public class SqlSessionFactoryUtil {
    private SqlSessionFactoryUtil() {
    }

    /**
     * 获取mybatis的sqlSessionFactory
     *
     * @param dataSource              数据源
     * @param mappingLocation         xml路径
     * @param typeAliasesPackage      model路径
     * @param configurationCustomizer mybatis自定义配置对象
     * @return SqlSessionFactory
     * @throws Exception 异常信息
     */
    public static SqlSessionFactory getSqlSessionFactory(DataSource dataSource, String mappingLocation, String typeAliasesPackage, ConfigurationCustomizer configurationCustomizer) throws Exception {
        return getSqlSessionFactory(dataSource, mappingLocation, typeAliasesPackage, null, configurationCustomizer);
    }

    /**
     * 获取mybatis的sqlSessionFactory
     *
     * @param dataSource              数据源
     * @param mappingLocation         xml路径
     * @param typeAliasesPackage      model路径
     * @param typeHandlersPackage     typeHandlers包路径
     * @param configurationCustomizer mybatis自定义配置对象
     * @return SqlSessionFactory
     * @throws Exception 异常信息
     */
    public static SqlSessionFactory getSqlSessionFactory(DataSource dataSource, String mappingLocation, String typeAliasesPackage,
                                                         String typeHandlersPackage,
                                                         ConfigurationCustomizer configurationCustomizer) throws Exception {
        //在基本的 MyBatis 中,session 工厂可以使用 SqlSessionFactoryBuilder 来创建。
        //而在 MyBatis-spring 中,则使用SqlSessionFactoryBean 来替代：
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //如果重写了 SqlSessionFactory 需要在初始化的时候手动将 mapper 地址 set到 factory 中，否则会报错：
        //org.apache.ibatis.binding.BindingException: Invalid bound statement (not found)
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mappingLocation));
        //下面这个setTypeAliasesPackage无效，是mybatis集成springBoot的一个bug，暂时未能解决
        bean.setTypeAliasesPackage(typeAliasesPackage);
        if (null != typeHandlersPackage) {
            bean.setTypeHandlersPackage(typeHandlersPackage);
        }
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        //因为没有用mybatis-springBoot自动装配，所以需要手动将configuration装配进去，要不然自定义的map key驼峰转换不起作用
        configurationCustomizer.customize(configuration);

        bean.setConfiguration(configuration);
        return bean.getObject();
    }
}
