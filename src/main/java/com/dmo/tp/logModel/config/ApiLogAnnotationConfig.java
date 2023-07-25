package com.dmo.tp.logModel.config;

import com.dmo.tp.logModel.annotation.parser.ApiLogCommonOperation;
import com.dmo.tp.logModel.annotation.parser.ApiLogCommonOperationImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiLogAnnotationConfig {

    @Bean
    public ApiLogCommonOperation apiLogCommonOperation() {
        return new ApiLogCommonOperationImpl();
    }

}
