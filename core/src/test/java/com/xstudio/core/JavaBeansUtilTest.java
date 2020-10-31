package com.xstudio.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaBeansUtilTest {

    @Test
    void getCamelCaseString() {
        String camelCase = JavaBeansUtil.getCamelCaseString("camel_case", false);
        Assertions.assertEquals("camelCase", camelCase);
    }

    @Test
    void getCamelCaseString2() {
        String camelCase = JavaBeansUtil.getCamelCaseString("camel_case", true);
        Assertions.assertEquals("CamelCase", camelCase);
    }
}