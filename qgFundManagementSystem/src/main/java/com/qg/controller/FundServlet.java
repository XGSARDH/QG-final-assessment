package com.qg.controller;

import com.qg.factory.DaoFactory;
import com.qg.po.UserFund;
import com.qg.service.FundService;
import com.qg.service.GroupAdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/FundServlet")
public class FundServlet extends BaseServlet {


    /**
     * 群组内分配资金
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void groupAllocation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Long activeUserId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String passiveUserId = request.getParameter("user_id");
        String groupId = request.getParameter("groupId");
        String amount = request.getParameter("number");

        if (!amount.matches("^\\+?\\d+(\\.\\d+)?$")) {
            response.getWriter().write("{\"status\":201, \"message\":\"输入不合法\",\"data\":" + 0 + "}");
        }

        String groupAllocation = FundService.getInstance().groupAllocation(Long.valueOf(passiveUserId), Long.valueOf(groupId), amount, activeUserId);

        response.getWriter().write("{\"status\":200, \"message\":\"完成\",\"data\":" + groupAllocation + "}");
    }


    /**
     * 群组内收回指定金额的资金
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void groupWithdrawal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Long activeUserId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String passiveUserId = request.getParameter("user_id");
        String groupId = request.getParameter("groupId");
        String amount = request.getParameter("number");

        if (!amount.matches("^\\+?\\d+(\\.\\d+)?$")) {
            response.getWriter().write("{\"status\":201, \"message\":\"输入不合法\",\"data\":" + 0 + "}");
        }

        String groupAllocation = FundService.getInstance().groupWithdrawal(Long.valueOf(passiveUserId), Long.valueOf(groupId), amount, activeUserId);

        response.getWriter().write("{\"status\":200, \"message\":\"完成\",\"data\":" + groupAllocation + "}");

    }


    /**
     * 群组内收回该成员全部群组资金
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void groupWithdrawalAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Long activeUserId = HelpServlet.getInstance().getUserIdFromJwt(request, response);
        String passiveUserId = request.getParameter("user_id");
        String groupId = request.getParameter("groupId");
        String amount = request.getParameter("number");

        if (!amount.matches("^\\+?\\d+(\\.\\d+)?$")) {
            response.getWriter().write("{\"status\":201, \"message\":\"输入不合法\",\"data\":" + 0 + "}");
        }

        String groupAllocation = FundService.getInstance().groupWithdrawalAll(Long.valueOf(passiveUserId), Long.valueOf(groupId), activeUserId);

        response.getWriter().write("{\"status\":200, \"message\":\"完成\",\"data\":" + groupAllocation + "}");

    }

    /**
     * 查询浏览网页者的个人id对应的名下账户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void viewUserFunds(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);

        String viewUserFunds = FundService.getInstance().viewUserFunds(userId);

        if (!viewUserFunds.equals("")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + viewUserFunds + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"该用户权限不足或群组不存在\",\"data\":" + 0 + "}");
        }
    }

    /**
     * 传一个搜索群组Id来查询这个id名下群组账户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findGroupFund(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);

        Long searchGroupId = Long.valueOf(request.getParameter("group_id"));
        String searchForUserFundId = FundService.getInstance().viewGroupFunds(searchGroupId);

        if (!searchForUserFundId.equals("")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + searchForUserFundId + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"该用户权限不足或群组不存在\",\"data\":" + 0 + "}");
        }
    }


    /**
     * 传一个搜索用户Id来查询这个id名下私人账户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchForUserFundId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);

        Long searchUserId = Long.valueOf(request.getParameter("user_id"));
        String searchForUserFundId = FundService.getInstance().viewUserFunds(searchUserId);

        if (!searchForUserFundId.equals("")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + searchForUserFundId + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"该用户权限不足或群组不存在\",\"data\":" + 0 + "}");
        }
    }

    /**
     * 发起转账
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void transferFunds(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long activeUserId = HelpServlet.getInstance().getUserIdFromJwt(request, response);

        Long passiveUserId = Long.valueOf(request.getParameter("user_id"));
        UserFund passiveUseFond = null;
        List<UserFund> byUserId = DaoFactory.getUserFundDao().findByUserId(passiveUserId);
        if (byUserId == null) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求失败\",\"data\":\"" + "目标用户不存在" + "\"}");
        }
        for (UserFund userFund : byUserId) {
            if(userFund.getGroupId().equals(0L)) {
                passiveUseFond = userFund;
            }
        }
        if (passiveUseFond == null) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求失败\",\"data\":\"" + "目标用户不存在" + "\"}");
        }
        Long activeUserFundId = Long.valueOf(request.getParameter("source_account_id"));

        Long passiveUserFundId = passiveUseFond.getUserFundId();

        String fund = String.valueOf(request.getParameter("amount"));

        String transferFunds = FundService.getInstance().transferFunds(activeUserFundId, passiveUserFundId, fund, activeUserId);

        if (transferFunds.equals("Success")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":\"" + transferFunds + "\"}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"该用户权限不足或群组不存在\",\"data\":" + 0 + "}");
        }
    }
    /**
     * 发起转账_群组
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void transferFunds_group(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long activeUserId = HelpServlet.getInstance().getUserIdFromJwt(request, response);

        Long passiveGroupId = Long.valueOf(request.getParameter("group_id"));
        UserFund passiveUseFond = null;
        List<UserFund> byGroupId = DaoFactory.getUserFundDao().findByGroupId(passiveGroupId);
        if (byGroupId == null) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求失败\",\"data\":\"" + "目标用户不存在" + "\"}");
        }
        for (UserFund userFund : byGroupId) {
            if(userFund.getUserId().equals(0L)) {
                passiveUseFond = userFund;
            }
        }
        if (passiveUseFond == null) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求失败\",\"data\":\"" + "目标用户不存在" + "\"}");
        }
        Long activeUserFundId = Long.valueOf(request.getParameter("source_account_id"));
        Long passiveUserFundId = passiveUseFond.getUserFundId();
        String fund = String.valueOf(request.getParameter("amount"));

        System.out.println(activeUserFundId);
        System.out.println(passiveUserFundId);
        System.out.println(fund);
        System.out.println(activeUserId);
        String transferFunds = FundService.getInstance().transferFunds(activeUserFundId, passiveUserFundId, fund, activeUserId);

        if (transferFunds.equals("Success")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":\"" + transferFunds + "\"}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"该用户权限不足或群组不存在\",\"data\":" + transferFunds + "}");
        }
    }

    /**
     * 请求转账
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void ApplyToFormGave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long userId = HelpServlet.getInstance().getUserIdFromJwt(request, response);

        Long activeUserFundId = Long.valueOf(request.getParameter("active_user_fund_id"));
        Long passiveUserFundId = Long.valueOf(request.getParameter("passive_user_fund_id"));
        String fund = String.valueOf(request.getParameter("fund"));
        String description = String.valueOf(request.getParameter("description"));
        String searchForUserFundId = FundService.getInstance().applyToFormGave(activeUserFundId, passiveUserFundId, fund, description);

        if (!searchForUserFundId.equals("已向对方发出付款请求")) {
            response.getWriter().write("{\"status\":200, \"message\":\"请求成功\",\"data\":" + searchForUserFundId + "}");
        } else {
            response.getWriter().write("{\"status\":201, \"message\":\"该用户权限不足或群组不存在\",\"data\":" + 0 + "}");
        }
    }


}
