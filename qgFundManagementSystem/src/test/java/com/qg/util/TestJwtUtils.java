package com.qg.util;

import com.qg.util.JwtUtils.JwtUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestJwtUtils {
    @Test
    public void TestGenerateJwt() {
        Map<String , Object> claims = new HashMap<>();
        claims.put("id", "123");
        claims.put("username","456");
        claims.put("phone","789");
        String s = JwtUtils.generateJwt(claims);
        System.out.println(s);
    }
}
