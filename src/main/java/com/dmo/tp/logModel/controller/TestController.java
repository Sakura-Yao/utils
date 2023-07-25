package com.dmo.tp.logModel.controller;

import com.alibaba.fastjson2.JSONObject;
import com.dmo.tp.logModel.annotation.ApiLog;
import com.dmo.tp.logModel.service.TestApiService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logModel")
public class TestController {

    @Resource
    private TestApiService testApiService;

    @GetMapping("test1")
    @ApiLog()
    public JSONObject test(@RequestParam("id") String id) {
        return testApiService.test();
    }

}
