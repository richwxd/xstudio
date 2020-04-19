package com.xstudio.spring.api.gateway;

import com.xstudio.core.ErrorConstant;
import com.xstudio.core.Msg;
import com.xstudio.spring.api.gateway.annotation.ApiGateway;
import com.xstudio.spring.api.gateway.annotation.ApiGatewayStrategy;
import com.xstudio.spring.api.gateway.entity.Limit;
import com.xstudio.spring.api.gateway.service.ITimesService;
import com.xstudio.spring.api.gateway.strategy.AbstractStrategy;
import com.xstudio.spring.web.SpringContextProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * @author xiaobiao
 * @version 2020/2/12
 */
@Aspect
public class AspectApiGateway {
    /**
     * 转义字符切面
     */
    @Pointcut("@annotation(com.xstudio.spring.api.gateway.annotation.ApiGateway)")
    public void apiGatewayPointcut() {
        // 定义切面annotation注解方法
    }

    @Around("apiGatewayPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Annotation[] annotations = signature.getMethod().getAnnotations();

        ApiGateway annotation = getAnnotation(annotations);

        ApiGatewayStrategy[] strategies = annotation.strategies();

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        assert requestAttributes != null;


        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        AbstractStrategy strategy;
        for (ApiGatewayStrategy apiGatewayStrategy : strategies) {
            strategy = apiGatewayStrategy.type().newInstance();
            Class<? extends ITimesService> timesService = apiGatewayStrategy.timesService();
            Limit limit = new Limit(apiGatewayStrategy.times(), apiGatewayStrategy.unit());
            if (!"ITimesService".equals(timesService.getSimpleName())) {
                limit = timesService.newInstance().times(getUri(request));
            }

            boolean checkResult = strategy.check(
                    getUri(request), request,
                    limit.getTimes(),
                    limit.getUnit(),
                    (RedisTemplate<Object, Object>) SpringContextProvider.getBean(annotation.redisTemplateBean())
            );
            if (Boolean.FALSE.equals(checkResult)) {
                Msg<Object> msg = new Msg<>();
                msg.setResult(ErrorConstant.API_LIMIT, ErrorConstant.API_LIMIT_MSG);
                return msg;
            }
        }

        // 动态修改其参数
        // 注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)

        // 如果这里不返回result，则目标对象实际返回值会被置为null
        return joinPoint.proceed(args);
    }

    private String getUri(HttpServletRequest request) {
        return "apigateway:" + request.getRequestURI() + ":strategy:";
    }

    private ApiGateway getAnnotation(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof ApiGateway) {
                return (ApiGateway) annotation;
            }
        }
        return null;
    }

}
