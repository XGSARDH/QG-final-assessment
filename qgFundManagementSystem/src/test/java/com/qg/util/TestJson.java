package com.qg.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qg.service.UserService;
import org.junit.Test;

import java.text.ParseException;

public class TestJson {
    @Test
    public void whenGenerateJson_thanGenerationCorrect() throws ParseException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < 2; i++) {
            jsonObject = new JSONObject();
            jsonObject.put("userId", 10);
            jsonObject.put("userName", "Tom " + i);
            jsonArray.add(jsonObject);
        }
        String jsonOutput = jsonArray.toJSONString();
        System.out.println(jsonOutput);
        String jsonOutput2 = jsonObject.toJSONString();
        System.out.println(jsonOutput2);
    }

    @Test
    public void t() {
        UserService.getInstance().viewGroup();
    }

}
