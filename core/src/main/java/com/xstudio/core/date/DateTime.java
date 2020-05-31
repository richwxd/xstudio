package com.xstudio.core.date;

import java.util.Date;

/**
 * 日期扩展类
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
public class DateTime extends Date {
    /**
     * 开始时间
     */
    private Date start;

    /**
     * 结束时间
     */
    private Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
