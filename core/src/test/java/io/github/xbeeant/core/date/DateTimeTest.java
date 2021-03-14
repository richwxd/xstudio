package io.github.xbeeant.core.date;

import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author xiaobiao
 * @version 2020/2/2
 */
class DateTimeTest {
    @Test
    void testDateTime() {
        DateTime dateTime = new DateTime();
        dateTime.setStart(new Date());
        dateTime.setEnd(new Date());
    }
}