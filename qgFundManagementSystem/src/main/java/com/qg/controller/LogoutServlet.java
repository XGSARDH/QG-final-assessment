package com.qg.controller;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends BaseServlet {
    public void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

//        JSONObject jsonResponse = new JSONObject();
//        jsonResponse.put("message", "Logout successful");
//        jsonResponse.put("clearToken", true);
//
//        out.print(jsonResponse.toString());
//        out.flush();

        response.getWriter().write("{\"status\":200, \"message\":\"登录成功\", \"clearToken\":\"" + true + "\"}");
    }
}
