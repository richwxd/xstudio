package com.xstudio.aop;

import com.xstudio.aop.annotation.UnEscapeField;
import com.xstudio.core.JavaBeansUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2020/2/16
 */
@Aspect
@Component
public class EscapeSpecialStringAspect {

    /**
     * 环绕通知
     *
     * @param joinPoint {@link JoinPoint}
     */
    @Before("escapeSpecialStringPointcut()")
    public void doAround(JoinPoint joinPoint) {
        Object[] arg = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();

        List<String> unEscapeFeilds = new ArrayList<>();
        if (parameterAnnotations.length > 0) {
            unEscapeFeilds = getUnEscapeFields(parameterAnnotations, unEscapeFeilds);
        }
        for (Object obj : arg) {
            if (obj == null) {
                continue;
            }
            List<Field> fields = getObjectFields(obj.getClass());
            escapeFields(unEscapeFeilds, obj, fields);
        }
    }

    private List<String> getUnEscapeFields(Annotation[][] parameterAnnotations, List<String> unEscapeFeilds) {
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof UnEscapeField) {
                    unEscapeFeilds = Arrays.asList(((UnEscapeField) annotation).fields());
                }
            }
        }
        return unEscapeFeilds;
    }

    private List<Field> getObjectFields(Class<?> aClass) {
        // 临时类
        Class<?> tempClass = aClass;
        List<Field> fields = new ArrayList<>();
        // 当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass != null && !"java.lang.object".equalsIgnoreCase(tempClass.getName())) {
            fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            // 得到父类,然后赋给临时类
            tempClass = tempClass.getSuperclass();
        }
        return fields;
    }

    private void escapeFields(List<String> unEscapeFeilds, Object obj, List<Field> fields) {
        for (Field field : fields) {
            if (field.getType().isAssignableFrom(String.class)) {
                String fieldName = field.getName();
                if (unEscapeFeilds.contains(fieldName)) {
                    continue;
                }

                Object value = JavaBeansUtil.getter(obj, fieldName);
                /*
                 * enum 或 null 不进行字符转义
                 */
                if (null != value && !(value instanceof Enum)) {
                    value = JsonpUtil.clean((String) value);
                    doFieldValueSet(obj, fieldName, value);
                }
            }
        }
    }

    private void doFieldValueSet(Object obj, String fieldName, Object value) {
        JavaBeansUtil.setter(obj, fieldName, value);
    }

    /**
     * 转义字符切面
     */
    @Pointcut("@annotation(com.xstudio.aop.annotation.EscapeSpecialString)")
    public void escapeSpecialStringPointcut() {
        // 定义切面annotation注解方法
    }
}
