package com.dmo.tp.logModel.annotation.parser;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.dmo.tp.logModel.annotation.ApiLog;
import com.dmo.tp.logModel.bean.log.ILog;
import com.dmo.tp.logModel.util.IpUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
@Slf4j
// 给予AOP实现注解方法
public class ApiLogParser {

    private static final String PROJECT_PACKAGE = "com.dmo.tp.logModel";

    @Resource
    private ApiLogCommonOperation apiLogCommonOperation;

    @Pointcut("@annotation(com.dmo.tp.logModel.annotation.ApiLog)")
    public void apiLog() {
    }

    // 环绕通知
    @Around("apiLog()")
    public Object around(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        ILog iLog = ILog.build();
        long startTime = System.currentTimeMillis();
        setRequestParams(request, iLog);
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            setResponseParams(proceed, iLog);
            finish(iLog, startTime, joinPoint);
        } catch (Throwable e) {
            iLog.setTimeConsuming(System.currentTimeMillis() - startTime);
            if (proceed == null) {
                setException(iLog, e);
            }
        }
        // 保存日志
        apiLogCommonOperation.saveLog(iLog);
        return proceed;
    }

    // 获取用户ID
    private void setUserId (ILog bean) {
        // 默认是通过header去获取ID 其他方法可以修改此处
        bean.setUserId(apiLogCommonOperation.getUserId());
    }

    private void setException(ILog bean, Throwable e) {
        bean.setLogType(ApiLog.LogType.EXCEPTION.getCode());
        bean.setExceptionMessage(e.getMessage());
        bean.setExceptionStack(Arrays.toString(Arrays.stream(e.getStackTrace()).filter(stackTraceElement -> stackTraceElement.getClassName().startsWith(PROJECT_PACKAGE)).toArray()));
    }

    // 获取返回体参数
    private void setResponseParams(Object proceed, ILog bean) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        if (response != null) {
            bean.setResponseStatus(response.getStatus());
        }
        bean.setResponseBody(JSONObject.toJSONString(proceed));
    }

    // 获取请求参数
    private void setRequestParams(HttpServletRequest request, ILog bean) {
        String ipAddress = IpUtil.getIpAddress(request);
        bean.setRequestIp(ipAddress);
        bean.setUri(request.getRequestURI());
        bean.setMethod(request.getMethod());
        bean.setRequestBody(JSON.toJSONString(request.getParameterMap()));
        this.setUserId(bean);
    }

    private void finish(ILog iLog, long startTime, ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        ApiLog annotation = method.getAnnotation(ApiLog.class);
        iLog.setTimeConsuming(System.currentTimeMillis() - startTime);
        iLog.setResponseDate(new Date());
        iLog.setLogType(annotation.logType().getCode());
    }
}
