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
        if(createGroupList.equals("[]")) {
            response.getWriter().write("{\"status\":200, \"message\":\"暂无待审核列表\", \"data\":\"" + createGroupList + "\"}");
        }else {
            response.getWriter().write("{\"status\":200, \"message\":\"获得创建群组列表成功\", \"data\":" + createGroupList + "}");
        }
    }

    public void sureCreateGroup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        if(userId == 0L) {
            response.getWriter().write("{\"status\":201, \"message\":\"通过审核出错,id错误\", \"data\":" + 0 + "}");
            return;
        }
        String beDoUserId = request.getParameter("user_id");
        String permissionChangeId = request.getParameter("permission_change_id");
        String sureCreateGroup = AdminService.getInstance().sureCreateGroup(Long.valueOf(beDoUserId), Long.valueOf(permissionChangeId));

        response.getWriter().write("{\"status\":200, \"message\":\"通过审核成功\", \"data\":\"" + sureCreateGroup + "\"}");

    }

    public void refuseCreateGroup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        if(userId == 0L) {
            response.getWriter().write("{\"status\":201, \"message\":\"通过审核出错\", \"data\":" + 0 + "}");
            return;
        }
        String beDoUserId = request.getParameter("user_id");
        String permissionChangeId = request.getParameter("permission_change_id");
        String sureCreateGroup = AdminService.getInstance().refuseCreateGroup(Long.valueOf(beDoUserId), Long.valueOf(permissionChangeId));

        response.getWriter().write("{\"status\":200, \"message\":\"已成功退回\", \"data\":\"" + sureCreateGroup + "\"}");

    }

}
