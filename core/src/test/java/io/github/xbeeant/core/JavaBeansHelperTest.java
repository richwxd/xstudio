package io.github.xbeeant.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaBeansHelperTest {

    @Test
    void getCamelCaseString() {
        String camelCase = JavaBeansHelper.getCamelCaseString("camel_case", false);
        Assertions.assertEquals("camelCase", camelCase);
    }

    @Test
    void getCamelCaseString2() {
        String camelCase = JavaBeansHelper.getCamelCaseString("camel_case", true);
        Assertions.assertEquals("CamelCase", camelCase);
    }
}