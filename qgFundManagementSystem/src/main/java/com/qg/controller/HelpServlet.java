package com.qg.controller;

import com.qg.dao.impl.GroupImpl;
import com.qg.util.JwtUtils.JwtUtils;
import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelpServlet {

    static HelpServlet instance = null;

    public static HelpServlet getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (HelpServlet.class) {
                if (instance == null) {
                    instance = new HelpServlet();
                }
            }
        }
        return instance;
    }

    public HelpServlet() {
    }

    public Long getUserIdFromJwt(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("已进入校验");
        String authHeader = (request).getHeader("Authorization");
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 提取JWT Token，去除Bearer前缀
            jwt = authHeader.substring(7);
        } else {
            response.sendRedirect("blank.html");
            return 0L;
        }
        response.setContentType("application/json;charset=UTF-8");
        Claims claims = JwtUtils.parseJWT(jwt);
        String userId = claims.get("userId", String.class);
        return Long.valueOf(userId);
    }
}
