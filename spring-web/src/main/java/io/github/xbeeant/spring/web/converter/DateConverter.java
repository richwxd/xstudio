package io.github.xbeeant.spring.web.converter;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

/**
 * @author xiaobiao
 * @version 2020/2/2
 */
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        if (source.isEmpty()) {
            return null;
        }

        try {
            return DateUtils.parseDate(source);
        } catch (ParseException e) {
            return null;
        }
    }
}
