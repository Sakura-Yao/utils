package com.dmo.tp.logModel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 自定义日志注解
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiLog {

    // 日志类型 - 1：登录日志；2：操作日志；3：异常日志
    LogType logType() default LogType.OPERATION;

    // 日志类型枚举类
    enum LogType {
        LOGIN(1, "登录日志"),
        OPERATION(2, "操作日志"),
        EXCEPTION(3, "异常日志");

        private final Integer code;
        private final String name;

        LogType(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

}
