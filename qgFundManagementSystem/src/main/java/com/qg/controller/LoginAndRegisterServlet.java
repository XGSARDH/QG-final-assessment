package com.qg.controller;

import com.qg.service.TouristService;
import com.qg.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginAndRegisterServlet")
public class LoginAndRegisterServlet extends BaseServlet {

    public void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String username = request.getParameter("userId");
        String password = request.getParameter("password");
        Integer loginStatus = TouristService.getInstance().login(username, password);
        if(loginStatus == 1){
            response.getWriter().write("{\"status\":200, \"message\":\"登录成功\"}");
        } else {
            response.getWriter().write("{\"status\":402, \"message\":\"登录失败\"}");
        }

    }

}
