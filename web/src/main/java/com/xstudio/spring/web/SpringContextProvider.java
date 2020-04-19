package com.xstudio.spring.web;

import org.springframework.context.ApplicationContext;

/**
 * @author xiaobiao
 * @version 2020/2/12
 */
public class SpringContextProvider {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextProvider.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static Object getBean(Class<?> requiredType) {
        return applicationContext.getBean(requiredType);
    }
}
