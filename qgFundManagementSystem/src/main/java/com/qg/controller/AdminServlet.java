package com.qg.controller;

import com.qg.factory.DaoFactory;
import com.qg.po.PermissionChange;
import com.qg.service.AdminService;
import com.qg.util.JwtUtils.JwtUtils;
import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdminServlet")
public class AdminServlet extends BaseServlet {

    public void viewCreateGroupList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        if(userId == 0L) {
            response.getWriter().write("{\"status\":201, \"message\":\"获得创建群组列表失败\", \"data\":" + 0 + "}");
            return;
        }
        String createGroupList = AdminService.getInstance().viewCreateGroupList();
        response.getWriter().write("{\"status\":200, \"message\":\"获得创建群组列表成功\", \"data\":" + createGroupList + "}");

    }

}
