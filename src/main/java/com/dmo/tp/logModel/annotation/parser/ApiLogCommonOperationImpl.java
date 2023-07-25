package com.dmo.tp.logModel.annotation.parser;

import com.dmo.tp.logModel.bean.log.ILog;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiLogCommonOperationImpl implements ApiLogCommonOperation {

    @Override
    public Integer getUserId() {
        return 123;
    }

    @Override
    public void saveLog(ILog bean) {
        log.info("保存日志到数据库 => {}", bean);
    }
}
