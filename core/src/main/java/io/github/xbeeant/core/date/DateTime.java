package io.github.xbeeant.core.date;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期扩展类
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
public class DateTime extends Date {
    /**
     * 结束时间
     */
    private Date end;
    /**
     * 开始时间
     */
    private Date start;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * 减去
     *
     * @param amount 数量
     * @param field  单位
     * @return 日期
     */
    public Date minus(int amount, int field) {
        return plus(-amount, field);
    }

    /**
     * 加上
     *
     * @param amount 数量
     * @param field  单位
     * @return 日期
     */
    public Date plus(int amount, int field) {
        Calendar time = Calendar.getInstance();
        time.setTime(this);
        time.add(field, amount);
        return time.getTime();
    }
}
