package com.dmo.tp.logModel.bean.log;

import lombok.Data;

import java.util.Date;

@Data
public class ILog {

    // 日志类型 - 1：登录日志；2：操作日志；3：异常日志
    private Integer logType;

    // URI
    private String uri;

    // 请求人ID
    private Integer userId;

    // 请求类型 - GET/POST
    private String method;

    // 请求IP
    private String requestIp;

    // 请求参数
    private String requestBody;

    // 请求时间
    private Date requestDate;

    // 相应时间
    private Date responseDate;

    // 响应状态码
    private Integer responseStatus;

    // 响应结果
    private String responseBody;

    // 异常信息
    private String exceptionMessage;

    // 异常堆栈
    private String exceptionStack;

    // 请求耗时
    private Long timeConsuming;

    public static ILog build() {
        ILog iLog = new ILog();
        iLog.setRequestDate(new Date());
        return iLog;
    }

}
