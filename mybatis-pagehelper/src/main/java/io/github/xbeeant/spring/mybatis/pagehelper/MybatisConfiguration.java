package io.github.xbeeant.spring.mybatis.pagehelper;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * mybatis配置
 *
 * @author xiaobiao
 * @version 2020/2/3
 */
@org.springframework.context.annotation.Configuration
public class MybatisConfiguration {

    @Bean("mybatisConfigurationCustomizer")
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(Configuration configuration) {
                // customize
                configuration.addInterceptor(PageHelperInterceptor.getInterceptor());
            }
        };
    }
}
