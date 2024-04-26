package com.qg.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.qg.config.Role;
import com.qg.factory.DaoFactory;
import com.qg.po.Group;
import com.qg.po.GroupMember;
import com.qg.service.GroupAdminService;
import com.qg.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GroupAdminServlet")
public class GroupAdminServlet extends BaseServlet{

    public void toBeAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String groupId = request.getParameter("group_id");
        String passiveId = request.getParameter("passive_id");

        Integer toBeAdmin = GroupAdminService.getInstance().toBeAdmin(userId, Long.valueOf(groupId), Long.valueOf(passiveId));

        System.out.println(toBeAdmin);

        if (toBeAdmin == 1) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + "\"成功设为群组管理员\"" + "}");
        } else if (toBeAdmin == 2) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"双方有一方不在群组内\"" + "}");
        } else if (toBeAdmin == 3) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"执行操作对象权限不足\"" + "}");
        } else if (toBeAdmin == 4) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"受影响操作对象已退出群组\"" + "}");
        } else if (toBeAdmin == 5) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"权限已是管理员 或 成员是最高管理者\"" + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\" 出现错误 \"" + "}");
        }
    }

    public void toBeNormal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String groupId = request.getParameter("group_id");
        String passiveId = request.getParameter("passive_id");

        Integer toBeNormal = GroupAdminService.getInstance().toBeNormal(userId, Long.valueOf(groupId), Long.valueOf(passiveId));

        System.out.println(toBeNormal);

        if (toBeNormal == 1) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + "\"成功设为普通成员\"" + "}");
        } else if (toBeNormal == 2) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"双方有一方不在群组内\"" + "}");
        } else if (toBeNormal == 3) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"执行操作对象权限不足\"" + "}");
        } else if (toBeNormal == 4) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"受影响操作对象已退出群组\"" + "}");
        } else if (toBeNormal == 5) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"权限已是普通成员 或 成员是最高管理者\"" + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\" 出现错误 \"" + "}");
        }
    }

    public void toBeUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String groupId = request.getParameter("group_id");
        String passiveId = request.getParameter("passive_id");

        Integer toBeUser = GroupAdminService.getInstance().toBeUser(userId, Long.valueOf(groupId), Long.valueOf(passiveId));

        System.out.println(toBeUser);

        if (toBeUser == 1) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + "\"成功设为非群组成员\"" + "}");
        } else if (toBeUser == 2) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"双方有一方不在群组内\"" + "}");
        } else if (toBeUser == 3) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"执行操作对象权限不足\"" + "}");
        } else if (toBeUser == 4) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"受影响操作对象已退出群组\"" + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\" 出现错误 \"" + "}");
        }
    }

    public void updateGroup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String groupName = request.getParameter("group_name");
        String description = request.getParameter("description");
        String groupId = request.getParameter("group_id");

        Integer updateGroup = GroupAdminService.getInstance().updateGroup(userId, Long.valueOf(groupId), groupName, description);

        System.out.println(updateGroup);

        if (updateGroup == 1) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + "\"成功\"" + "}");
        } else if (updateGroup == 2) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"群组与该成员没有关系\"" + "}");
        } else if (updateGroup == 3) {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"该成员没有足够的权限\"" + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"行为执行出现错误\",\"data\":" + "\"行动出错\"" + "}");
        }
    }

    public void findAllGroupAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);

        response.setContentType("application/json;charset=UTF-8");
        String viewGroup = GroupAdminService.getInstance().viewGroupAdmin(userId);
        // 发送JSON响应
        response.getWriter().write("{\"status\":200, \"message\":\"成功\", \"groups\":" + viewGroup + "}");
    }

    /**
     * 查找单个群组信息包含资金
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void initGroupDetailFund(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String groupId = request.getParameter("group_id");

        String initGroupDetail = GroupAdminService.getInstance().initGroupDetailFund(userId, Long.valueOf(groupId));

        if (!initGroupDetail.equals("")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + initGroupDetail + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"该用户权限不足或群组不存在\",\"data\":" + 0 + "}");
        }
    }


}
