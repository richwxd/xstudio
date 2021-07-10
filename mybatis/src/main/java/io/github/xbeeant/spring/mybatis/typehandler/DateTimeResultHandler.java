package io.github.xbeeant.spring.mybatis.typehandler;

import io.github.xbeeant.core.date.DateTime;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


/**
 * @author xiaobiao
 * @version 2021/3/19
 */
public class DateTimeResultHandler implements TypeHandler<DateTime> {
    @Override
    public void setParameter(PreparedStatement ps, int i, DateTime parameter, JdbcType jdbcType) throws SQLException {
    }

    @Override
    public DateTime getResult(ResultSet rs, String columnName) throws SQLException {
        Date date = rs.getDate(columnName);
        if (null == date) {
            return null;
        }
        DateTime dateTime = new DateTime();
        dateTime.setTime(date.getTime());
        return dateTime;
    }

    @Override
    public DateTime getResult(ResultSet rs, int columnIndex) throws SQLException {
        Date date = rs.getDate(columnIndex);
        if (null == date) {
            return null;
        }
        DateTime dateTime = new DateTime();
        dateTime.setTime(date.getTime());
        return dateTime;
    }

    @Override
    public DateTime getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Date date = cs.getDate(columnIndex);
        if (null == date) {
            return null;
        }
        DateTime dateTime = new DateTime();
        dateTime.setTime(date.getTime());
        return dateTime;
    }
}
