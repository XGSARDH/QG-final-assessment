package com.qg.controller;

import com.qg.factory.DaoFactory;
import com.qg.service.GroupAdminService;
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

    /**
     * 查找jwt对应用户信息并返回
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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

    /**
     * 向管理员申请创建一个群组
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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

    /**
     * 查找单个群组信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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

    /**
     * 查找群组成员名单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void viewGroupIdforMemberList(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String groupId = request.getParameter("group_id");

        String viewGroupIdforMemberList = UserService.getInstance().viewGroupIdforMemberList(userId, Long.valueOf(groupId));

        System.out.println(viewGroupIdforMemberList);

        if (!viewGroupIdforMemberList.equals("")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + viewGroupIdforMemberList + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"该用户权限不足或群组不存在\",\"data\":" + 0 + "}");
        }
    }

//    updateUser
    public void ToBeMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String action_type = request.getParameter("action_type");
        String group_id = request.getParameter("group_id");
        String applicationContent = request.getParameter("applicationContent");

        if(!group_id.matches("^\\d+$\n")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":\"" + "groudId要是纯数字" + "\"}");
        }

        Long groudId = Long.valueOf(group_id);

        String ToBeGroupMember = UserService.getInstance().ToBeGroupMember(groudId, userId, applicationContent);

        if (ToBeGroupMember.equals("更新用户信息成功")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":\"" + "发送成功" + "\"}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"该用户权限不足或群组不存在\",\"data\":\"" + "处理失败, 请稍后重试, 或联系群组管理员" + "\"}");
        }

    }

    public void findMyGroup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);

        response.setContentType("application/json;charset=UTF-8");
        String viewGroup = UserService.getInstance().viewMyGroup(userId);
        // 发送JSON响应
        response.getWriter().write("{\"status\":200, \"message\":\"成功\", \"groups\":" + viewGroup + "}");
    }

    public void ApplyToJoinGroup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        response.setContentType("application/json;charset=UTF-8");

        String action_type = request.getParameter("action_type");
        String groupId = request.getParameter("group_id");
        String applicationContent = request.getParameter("applicationContent");

        UserService.getInstance().ApplyToJoinGroup(Long.valueOf(groupId), userId);

        // 发送JSON响应
        response.getWriter().write("{\"status\":200, \"message\":\"成功\", \"groups\":" + "成功" + "}");
    }


}
