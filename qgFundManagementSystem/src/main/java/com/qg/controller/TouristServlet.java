package com.qg.controller;

import com.qg.service.UserService;
import com.qg.util.JwtUtils.JwtUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

@WebServlet("/LoginAndRegisterServlet")
public class TouristServlet extends BaseServlet {

    public void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        Integer loginStatus = com.qg.service.TouristService.getInstance().login(userId, password);

        if (loginStatus == 1) {
            // 生成JWT
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", userId);
            String token = JwtUtils.generateJwt(claims);

            // 将JWT发送给前端
            response.getWriter().write("{\"status\":200, \"message\":\"登录成功\", \"token\":\"" + token + "\"}");
        } else {
            response.getWriter().write("{\"status\":402, \"message\":\"登录失败\"}");
        }

    }

    public void register(HttpServletRequest request, HttpServletResponse response)
        throws  ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String userName = request.getParameter("userName");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Long registerStatus = null;
        try {
            registerStatus = com.qg.service.TouristService.getInstance().register(userName, nickname, password, email, phone);
        } catch (SQLException e) {
            if(e.getErrorCode() == 1062) {
                // 信息已被使用
                response.getWriter().write("{\"status\":203, \"message\":\"注册失败\"}");
            }
        }
        if(registerStatus == 1) {
            response.getWriter().write("{\"status\":200, \"message\":\"注册成功\"}");
        } else if(registerStatus == 0) {
            response.getWriter().write("{\"status\":402, \"message\":\"注册失败\"}");
        } else if(registerStatus == 2) {
            response.getWriter().write("{\"status\":201, \"message\":\"注册失败\"}");
        } else {
            response.getWriter().write("{\"status\":200, \"message\":\"注册成功\",\"userId\":" + registerStatus + "}");
        }
    }

    public void retrievePassword(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String passwordHash = request.getParameter("passwordHash");
        out.println(phoneNumber);
        out.println(email);
        out.println(passwordHash);
        Long registerStatus = com.qg.service.TouristService.getInstance().retrievePassword(phoneNumber, email, passwordHash);
        out.println(registerStatus);
        if(registerStatus == 0L) {
            response.getWriter().write("{\"status\":201, \"message\":\"找回密码失败\"}");
        } else {
            response.getWriter().write("{\"status\":200, \"message\":\"找回密码成功\", \"userId\":" + registerStatus + "}");
        }
    }

    public void findPublicAllGroup(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String viewGroup = UserService.getInstance().viewGroup();
        // 发送JSON响应
        response.getWriter().write("{\"status\":200, \"message\":\"找回密码成功\", \"groups\":" + viewGroup + "}");
    }
    public void findPublicOneGroup(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String groupId = request.getParameter("groupId");
        String viewGroup = UserService.getInstance().SearchGruop(Long.valueOf(groupId));
        // 发送JSON响应
        response.getWriter().write("{\"status\":200, \"message\":\"找回密码成功\", \"groups\":" + viewGroup + "}");
    }

}
