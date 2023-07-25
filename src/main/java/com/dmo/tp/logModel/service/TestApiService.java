package com.dmo.tp.logModel.service;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TestApiService {

    public JSONObject test() {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("msg", "success");
        json.put("data", UUID.randomUUID().toString());
        return json;
    }

}
