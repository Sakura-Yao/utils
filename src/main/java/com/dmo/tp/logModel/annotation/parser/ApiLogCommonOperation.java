package com.dmo.tp.logModel.annotation.parser;

import com.dmo.tp.logModel.bean.log.ILog;


// 自定义日志注解通用方法接口
public interface ApiLogCommonOperation {

    Integer getUserId();

    void saveLog(ILog bean);

}
