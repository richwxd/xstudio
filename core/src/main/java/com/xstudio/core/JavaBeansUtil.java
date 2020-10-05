package com.xstudio.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author xiaobiao
 * @version 2020/2/16
 */
public class JavaBeansUtil {
    private static final Logger logger = LoggerFactory.getLogger(JavaBeansUtil.class);

    private JavaBeansUtil() {
        throw new UnsupportedOperationException("JavaBeansUtil can't be instantiated");
    }

    /**
     * @param obj       操作的对象
     * @param fieldName 操作的属性值
     * @param value     设置的值
     */
    public static void setter(Object obj, String fieldName, Object value) {
        String methodName = getSetterMethodName(fieldName);
        try {
            Method setMethod = obj.getClass().getMethod(methodName, value.getClass());
            setMethod.invoke(obj, value);
        } catch (Exception e) {
            logger.warn("没有 {} 对应的set方法 值 \"{}\"", fieldName, value);
        }
    }

    /**
     * getter方法
     *
     * @param obj       对象
     * @param fieldName 字段
     * @return Object 值
     */
    public static Object getter(Object obj, String fieldName) {
        String methodName = getGetterMethodName(fieldName);
        try {
            Method getMethod = obj.getClass().getMethod(methodName);
            return getMethod.invoke(obj);
        } catch (Exception e) {
            try {
                return obj.getClass().getDeclaredField(fieldName).get(obj);
            } catch (Exception e1) {
                logger.error("获取{} get方法失败", fieldName, e);
            }
        }
        return null;
    }

    public static String getGetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();

        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }

        sb.insert(0, "get");

        return sb.toString();
    }


    public static String getSetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();

        sb.append(property);
        boolean upperFirstCharacter = Character.isLowerCase(sb.charAt(0)) && (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1)));
        if (upperFirstCharacter) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        sb.insert(0, "set");

        return sb.toString();
    }

    /**
     * Gets the camel case string.
     *
     * @param inputString             the input string
     * @param firstCharacterUppercase the first character uppercase
     * @return the camel case string
     */
    public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(c);
                    }
                    break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        } else {
            sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        }

        return sb.toString();
    }
}
