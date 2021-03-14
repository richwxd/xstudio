package io.github.xbeeant.spring.api.gateway;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.core.ErrorCodeConstant;
import io.github.xbeeant.spring.api.gateway.annotation.ApiGateway;
import io.github.xbeeant.spring.api.gateway.annotation.ApiGatewayStrategy;
import io.github.xbeeant.spring.api.gateway.entity.Limit;
import io.github.xbeeant.spring.api.gateway.service.ITimesService;
import io.github.xbeeant.spring.api.gateway.strategy.AbstractStrategy;
import io.github.xbeeant.spring.web.SpringContextProvider;
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
    @Pointcut("@annotation(io.github.xbeeant.spring.api.gateway.annotation.ApiGateway)")
    public void apiGatewayPointcut() {
        // 定义切面annotation注解方法
    }

    @Around("apiGatewayPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Annotation[] annotations = signature.getMethod().getAnnotations();

        ApiGateway annotation = getAnnotation(annotations);

        assert annotation != null;
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
                ApiResponse<Object> apiResponse = new ApiResponse<>();
                apiResponse.setResult(ErrorCodeConstant.API_LIMIT, ErrorCodeConstant.API_LIMIT_MSG);
                return apiResponse;
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
