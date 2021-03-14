package io.github.xbeeant.aop;

import org.aspectj.lang.JoinPoint;

/**
 * @author xiaobiao
 * @version 2020/2/16
 */
public class AspectJUtils {
    private AspectJUtils() {
        throw new UnsupportedOperationException("AspectJUtils can't be instantiated");
    }

    /**
     * 获取方法名
     *
     * @param joinPoint {@link JoinPoint}
     * @return 方法名
     */
    public static String getMethodName(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        String shortMethodNameSuffix = "(..)";
        if (methodName.endsWith(shortMethodNameSuffix)) {
            methodName = methodName.substring(0, methodName.length() - shortMethodNameSuffix.length());
        }
        return methodName;
    }
}
