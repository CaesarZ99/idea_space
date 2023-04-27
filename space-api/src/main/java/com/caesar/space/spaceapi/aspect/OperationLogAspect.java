package com.caesar.space.spaceapi.aspect;

import com.caesar.space.spaceapi.annotations.OperationLog;
import com.caesar.space.spaceapi.responce.JsonResponse;
import com.caesar.space.spaceapi.util.LogUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * <h3>OperationLog</h3>
 * <p>操作日志切面</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-27 13:19
 **/
@Aspect
@Component
public class OperationLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);
    @Pointcut("@annotation(com.caesar.space.spaceapi.annotations.OperationLog)")
    public void addOperationLog(){}

    @Around("addOperationLog()")
    public Object saveOperationLog(ProceedingJoinPoint joinPoint){
        OperationLog annotation = getAnnotation(joinPoint);
        try {
            // 如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
            JsonResponse proceed = (JsonResponse) joinPoint.proceed();
            Integer code = proceed.getCode();
            if (code == 1) {
                System.out.println("存储成功信息");
            } else {
                System.out.println("存储失败信息");
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        LogUtil.logInfo("test",this.getClass());
        System.out.println(annotation.value());


        return JsonResponse.Builder.buildSuccess("addOperationLog success");
    }

    @AfterThrowing(value = "addOperationLog()", throwing = "throwable")
    public void operationExceptionLog(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }


    private static OperationLog getAnnotation(ProceedingJoinPoint joinPoint) {
        // 获取注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        OperationLog annotation = method.getAnnotation(OperationLog.class);
        return annotation;
    }

}
