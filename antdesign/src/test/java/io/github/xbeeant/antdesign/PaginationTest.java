package io.github.xbeeant.antdesign;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author xiaobiao
 * @version 2020/6/12
 */
class PaginationTest {

    private final Pagination pagination = new Pagination();

    @BeforeEach
    void setUp() {
        pagination.setCurrent(1);
        pagination.setPageSize(10);
        pagination.setTotal(100);
    }

    @Test
    void getTotal() {
        Assertions.assertEquals(100, pagination.getTotal());
    }

    @Test
    void getCurrent() {
        Assertions.assertEquals(1, pagination.getCurrent());
    }

    @Test
    void getPageSize() {
        Assertions.assertEquals(10, pagination.getPageSize());
    }
}