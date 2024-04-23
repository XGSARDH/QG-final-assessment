package com.qg.controller;

import com.qg.factory.DaoFactory;
import com.qg.service.UserService;
import com.qg.util.JwtUtils.JwtUtils;
import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {

    public void testConn(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String authHeader = (request).getHeader("Authorization");
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 提取JWT Token，去除Bearer前缀
            jwt = authHeader.substring(7);
        } else {
            response.sendRedirect("blank.html");
        }
        response.setContentType("application/json;charset=UTF-8");
        Claims claims = JwtUtils.parseJWT(jwt);

        // 解析字段
        String userId = claims.get("userId", String.class);
        System.out.println("userId" + userId);

        // jwt解析后全部
        String s = claims.toString();
        System.out.println("jwt: " + s);
        response.getWriter().write("{\"status\":200, \"message\":\"init成功\",\"userId\":" + 1 + "}");


    }

    public void viewUser(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String authHeader = (request).getHeader("Authorization");
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 提取JWT Token，去除Bearer前缀
            jwt = authHeader.substring(7);
        } else {
            response.sendRedirect("blank.html");
        }
        response.setContentType("application/json;charset=UTF-8");
        Claims claims = JwtUtils.parseJWT(jwt);

        // 解析字段
        String userId = claims.get("userId", String.class);
        System.out.println("userId: " + userId);

        String data = UserService.getInstance().viewProfile(Long.valueOf(userId));

        response.getWriter().write("{\"status\":200, \"message\":\"init成功\",\"data\":" + data + "}");


    }

    public void createGroup(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String authHeader = (request).getHeader("Authorization");
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 提取JWT Token，去除Bearer前缀
            jwt = authHeader.substring(7);
        } else {
            response.sendRedirect("blank.html");
        }
        response.setContentType("application/json;charset=UTF-8");
        Claims claims = JwtUtils.parseJWT(jwt);

        // 解析字段获取userId
        String userId = claims.get("userId", String.class);
        System.out.println("userId: " + userId);

        Integer status = UserService.getInstance().applyToGroup(Long.valueOf(userId));

        if (status == 1) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + 1 + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"请求失败, 你的申请创建群组数已最大, 请联系网站管理员进行处理\",\"data\":" + 0 + "}");
        }
    }

    public void initGroupDetail(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String groupId = request.getParameter("group_id");

        String initGroupDetail = UserService.getInstance().initGroupDetail(userId, Long.valueOf(groupId));

        if (!initGroupDetail.equals("")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + initGroupDetail + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"该用户权限不足或群组不存在\",\"data\":" + 0 + "}");
        }
    }

}
