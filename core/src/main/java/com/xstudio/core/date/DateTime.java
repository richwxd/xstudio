package com.xstudio.core.date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 日期扩展类
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DateTime extends Date {
    /**
     * 开始时间
     */
    private Date start;

    /**
     * 结束时间
     */
    private Date end;
}
