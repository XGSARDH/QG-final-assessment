package com.qg.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.qg.config.FondAction;
import com.qg.config.Role;
import com.qg.config.TypeOfUserOrGroup;
import com.qg.factory.DaoFactory;
import com.qg.po.*;
import com.qg.util.JwtUtils.JwtUtils;
import com.qg.util.connectPool.ConnectionPoolManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class FundService {
    private static volatile FundService instance;

    public static FundService getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (FundService.class) {
                if (instance == null) {
                    instance = new FundService();
                }
            }
        }
        return instance;
    }

    public FundService() {
    }

    /**
     * 返回查找用户字符串
     * @param userId
     * @return
     */
    public String viewUserFunds(Long userId) {
        List<UserFund> byUserId = DaoFactory.getUserFundDao().findByUserId(userId);
        JSONArray jsonArray = new JSONArray();
        for (UserFund userFund : byUserId) {
            JSONObject jsonObject = new JSONObject();
            if (userFund.getGroupId() != 0) {
                continue;
            }
            jsonObject.put("user_fund_id", userFund.getUserFundId());
            jsonObject.put("user_id", userFund.getUserId());
            jsonObject.put("user_health", userFund.getUserHealth());
            jsonObject.put("group_id", userFund.getGroupId());
            jsonObject.put("total_funds", userFund.getTotalFunds());
            jsonObject.put("available_funds", userFund.getAvailableFunds());
            jsonObject.put("frozen_funds", userFund.getFrozenFunds());
            jsonObject.put("gmt_create", userFund.getGmtCreate());
            jsonObject.put("gmt_modified", userFund.getGmtModified());
            System.out.println(userFund);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    /**
     * 返回查找用户字符串
     * @param groupId
     * @return
     */
    public String viewGroupFunds(Long groupId) {
        List<UserFund> byGroupId = DaoFactory.getUserFundDao().findByGroupId(groupId);
        JSONArray jsonArray = new JSONArray();
        for (UserFund userFund : byGroupId) {
            JSONObject jsonObject = new JSONObject();
            if (userFund.getUserId() != 0) {
                continue;
            }
            jsonObject.put("user_fund_id", userFund.getUserFundId());
            jsonObject.put("user_id", userFund.getUserId());
            jsonObject.put("user_health", userFund.getUserHealth());
            jsonObject.put("group_id", userFund.getGroupId());
            jsonObject.put("total_funds", userFund.getTotalFunds());
            jsonObject.put("available_funds", userFund.getAvailableFunds());
            jsonObject.put("frozen_funds", userFund.getFrozenFunds());
            jsonObject.put("gmt_create", userFund.getGmtCreate());
            jsonObject.put("gmt_modified", userFund.getGmtModified());
            System.out.println(userFund);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    /**
     * 群组内成员分配指定金额的资金
     * @return
     */
    public String groupAllocation(Long passiveUserId, Long groupId, String amount, Long activeUserId) {
        // 验证群组权限部分
        GroupMember passive = null;
        GroupMember active = null;
        List<GroupMember> byGroupId = DaoFactory.getGroupMemberDao().findByGroupId(groupId);
        if(byGroupId == null || byGroupId.isEmpty()) {
            return "该群组不存在合法成员";
        }
        for (GroupMember groupMember : byGroupId) {
            if (groupMember.getUserId().equals(passiveUserId)) {
                passive = groupMember;
            }
            if (groupMember.getUserId().equals(activeUserId)) {
                active = groupMember;
            }
        }
        if (active == null || passive == null) {
            return "群组成员存在异常, 操作被退回";
        }

        String activeRole = active.getRole();
        String passiveRole = passive.getRole();

        if (!activeRole.equals(String.valueOf(Role.GROUP_HIGHEST))) {
            if (!activeRole.equals(String.valueOf(Role.GROUP_ADMIN))) {
                return "您的权限不足以为变动该成员群组资金账户";
            } else if (passiveRole.equals(String.valueOf(Role.GROUP_ADMIN)) || passiveRole.equals(String.valueOf(Role.GROUP_HIGHEST))) {
                return "您的权限不足以为变动该成员群组资金账户";
            }
            return "您的权限不足以为变动该成员群组资金账户";
        }

        // 开始订单操作

        List<UserFund> fundByGroupId = DaoFactory.getUserFundDao().findByGroupId(groupId);

        UserFund passiveFund = null;
        UserFund groupFund = null;

        for (UserFund userFund : fundByGroupId) {
            if (userFund.getUserId().equals(0L)) {
                groupFund = userFund;
            }
            if (Objects.equals(userFund.getUserId(), passiveUserId)) {
                passiveFund = userFund;
            }
        }

        if (groupFund == null) {
            return "无法找到该群组账户, 请联系管理员";
        }
        if (passiveFund == null) {
            return "无法找到该成员群组账户, 请联系管理员";
        }

        return transferFunds(groupFund.getUserFundId() , passiveFund.getUserFundId(), amount, activeUserId);

    }

    /**
     * 群组内收回部分指定金额的资金
     * @return
     */
    public String groupWithdrawal(Long passiveUserId, Long groupId, String amount, Long activeUserId) {
        // 验证群组权限部分
        GroupMember passive = null;
        GroupMember active = null;
        List<GroupMember> byGroupId = DaoFactory.getGroupMemberDao().findByGroupId(groupId);
        if(byGroupId == null || byGroupId.isEmpty()) {
            return "该群组不存在合法成员";
        }
        for (GroupMember groupMember : byGroupId) {
            if (groupMember.getUserId().equals(passiveUserId)) {
                passive = groupMember;
            }
            if (groupMember.getUserId().equals(activeUserId)) {
                active = groupMember;
            }
        }
        if (active == null || passive == null) {
            return "群组成员存在异常, 操作被退回";
        }

        String activeRole = active.getRole();
        String passiveRole = passive.getRole();

        if (!activeRole.equals(String.valueOf(Role.GROUP_HIGHEST))) {
            if (!activeRole.equals(String.valueOf(Role.GROUP_ADMIN))) {
                return "您的权限不足以为变动该成员群组资金账户";
            } else if (passiveRole.equals(String.valueOf(Role.GROUP_ADMIN)) || passiveRole.equals(String.valueOf(Role.GROUP_HIGHEST))) {
                return "您的权限不足以为变动该成员群组资金账户";
            }
            return "您的权限不足以为变动该成员群组资金账户";
        }

        // 开始订单操作

        List<UserFund> fundByGroupId = DaoFactory.getUserFundDao().findByGroupId(groupId);

        UserFund passiveFund = null;
        UserFund groupFund = null;

        for (UserFund userFund : fundByGroupId) {
            if (userFund.getUserId().equals(0L)) {
                groupFund = userFund;
            }
            if (Objects.equals(userFund.getUserId(), passiveUserId)) {
                passiveFund = userFund;
            }
        }

        if (groupFund == null) {
            return "无法找到该群组账户, 请联系管理员";
        }
        if (passiveFund == null) {
            return "无法找到该成员群组账户, 请联系管理员";
        }

        return transferFunds(passiveFund.getUserFundId() , groupFund.getUserFundId(), amount, activeUserId);

    }
    /**
     * 群组内收回全部金额的资金
     * @return
     */
    public String groupWithdrawalAll(Long passiveUserId, Long groupId, Long activeUserId) {
        GroupMember passive = null;
        GroupMember active = null;
        List<GroupMember> byGroupId = DaoFactory.getGroupMemberDao().findByGroupId(groupId);
        if(byGroupId == null || byGroupId.isEmpty()) {
            return "该群组不存在合法成员";
        }
        for (GroupMember groupMember : byGroupId) {
            if (groupMember.getUserId().equals(passiveUserId)) {
                passive = groupMember;
            }
            if (groupMember.getUserId().equals(activeUserId)) {
                active = groupMember;
            }
        }
        if (active == null || passive == null) {
            return "群组成员存在异常, 操作被退回";
        }

        String activeRole = active.getRole();
        String passiveRole = passive.getRole();

        if (!activeRole.equals(String.valueOf(Role.GROUP_HIGHEST))) {
            if (!activeRole.equals(String.valueOf(Role.GROUP_ADMIN))) {
                return "您的权限不足以为变动该成员群组资金账户";
            } else if (passiveRole.equals(String.valueOf(Role.GROUP_ADMIN)) || passiveRole.equals(String.valueOf(Role.GROUP_HIGHEST))) {
                return "您的权限不足以为变动该成员群组资金账户";
            }
            return "您的权限不足以为变动该成员群组资金账户";
        }

        // 开始订单操作

        List<UserFund> fundByGroupId = DaoFactory.getUserFundDao().findByGroupId(groupId);

        UserFund passiveFund = null;
        UserFund groupFund = null;

        for (UserFund userFund : fundByGroupId) {
            if (userFund.getUserId().equals(0L)) {
                groupFund = userFund;
            }
            if (Objects.equals(userFund.getUserId(), passiveUserId)) {
                passiveFund = userFund;
            }
        }

        if (groupFund == null) {
            return "无法找到该群组账户, 请联系管理员";
        }
        if (passiveFund == null) {
            return "无法找到该成员群组账户, 请联系管理员";
        }

        return transferFunds(passiveFund.getUserFundId() , groupFund.getUserFundId(), passiveFund.getAvailableFunds(), activeUserId);

    }

    /**
     * 进行请求对方生成订单 (后面简称为"待支付订单") 操作
     * @param activeUserFundId
     * @param passiveUserFundId
     * @param fund
     * @param description
     * @return
     */
    public String applyToFormGave(Long activeUserFundId, Long passiveUserFundId, String fund, String description) {
        UserFund activeUserFund = DaoFactory.getUserFundDao().findByUserFundid(activeUserFundId).get(0);
        UserFund passiveUserFund = DaoFactory.getUserFundDao().findByUserFundid(passiveUserFundId).get(0);

        // 确定账户正常
        if (activeUserFund == null) {
            return "付款账户异常";
        }else if(activeUserFund.getUserHealth() == 0) {
            return "付款账户信用等级异常";
        }
        if (passiveUserFund == null) {
            return "收款账户异常";
        }else if(passiveUserFund.getUserHealth() == 0) {
            return "收款账户信用等级异常";
        }

        // 确定账户类型
        String activeType = null;
        String passiveType = null;
        if (activeUserFund.getUserId() == 0 || activeUserFund.getGroupId() != 0) {
            activeType = String.valueOf(TypeOfUserOrGroup.GROUP);
        }else {
            activeType = String.valueOf(TypeOfUserOrGroup.USER);
        }
        if (passiveUserFund.getUserId() == 0 || passiveUserFund.getGroupId() != 0) {
            passiveType = String.valueOf(TypeOfUserOrGroup.GROUP);
        }else {
            passiveType = String.valueOf(TypeOfUserOrGroup.USER);
        }

        Order order = new Order();
        order.setChangeType(String.valueOf(FondAction.APPLYTOFORMGAVE));
        order.setDescription(description);
        order.setActiveType(activeType);
        order.setActiveId(activeUserFundId);
        order.setAmount(fund);
        order.setPassiveType(passiveType);
        order.setPassiveId(passiveUserFundId);
        order.setGmtCreate(LocalDateTime.now());
        order.setGmtModified(LocalDateTime.now());

        Long orderId = null;

        try {
            orderId = DaoFactory.getOrderDao().save(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "已向对方发出付款请求";

    }

    /**
     * 对待支付订单进行确定支付操作
     * @param passiveUserFundId
     * @param orderId
     * @return
     */
    public String formGaveForApplicationApprove(Long passiveUserFundId, Long orderId, Long userId) {

        String jwt = JwtUtils.generateJwtByUserId(String.valueOf(userId));
        SessionService.getInstance().updateJwtToken(String.valueOf(userId), jwt);

        Order order = DaoFactory.getOrderDao().findByOrderId(orderId).get(0);

        if (!order.getPassiveId().equals(passiveUserFundId)) {
            return "您不需要支付该订单";
        }else if (!order.getChangeType().equals(String.valueOf(FondAction.APPLYTOFORMGAVE))) {
            return "您不需要支付该订单";
        }

        if (SessionService.getInstance().verifyJwtToken(userId, jwt) == 0) {
            return "您的环境不安全, 请稍后重试";
        }
        String statusApplication = formGave(order.getActiveId(), passiveUserFundId, order.getAmount());


        if (statusApplication.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            return "您生成订单失败, 请稍后再试";
        }

        orderId = Long.valueOf(statusApplication);
        if (SessionService.getInstance().verifyJwtToken(userId, jwt) == 0) {
            try {
                DaoFactory.getOrderDao().delete(orderId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return "您的环境不安全, 请稍后重试";
        }
        String giveStatus = approveOrder(orderId);

        if (giveStatus.equals("Order approved and funds updated")) {
            order.setChangeType(String.valueOf(FondAction.APPROVEAPPLICATION));
            try {

                DaoFactory.getOrderDao().update(order);
                DaoFactory.getOrderDao().delete(orderId);
                return "已支付待处理订单";
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            return "处理待处理订单失败, 恢复待处理订单";
        }

    }

    /**
     * 对待支付订单进行拒绝支付操作
     * @param passiveUserFundId
     * @param orderId
     * @return
     */
    public String formGaveForApplicationReject(Long passiveUserFundId, Long orderId, Long userId) {

        String jwt = JwtUtils.generateJwtByUserId(String.valueOf(userId));
        SessionService.getInstance().updateJwtToken(String.valueOf(userId), jwt);

        Order order = DaoFactory.getOrderDao().findByOrderId(orderId).get(0);

        if (!order.getPassiveId().equals(passiveUserFundId)) {
            return "您不需要支付该订单";
        }else if (!order.getChangeType().equals(String.valueOf(FondAction.APPLYTOFORMGAVE))) {
            return "您不需要支付该订单";
        }


        order.setChangeType(String.valueOf(FondAction.REJECTAPPLICATION));
        try {
            if (SessionService.getInstance().verifyJwtToken(userId, jwt) == 0) {
                return "您的环境不安全, 请稍后重试";
            }
            DaoFactory.getOrderDao().update(order);
            return "已拒绝支付待处理订单";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 进行生成订单操作, 线程安全
     * @param activeUserFundId
     * @param passiveUserFundId
     * @param fund
     * @return
     */
    public String formGave(Long activeUserFundId, Long passiveUserFundId, String fund) {
        UserFund activeUserFund = DaoFactory.getUserFundDao().findByUserFundid(activeUserFundId).get(0);
        UserFund passiveUserFund = DaoFactory.getUserFundDao().findByUserFundid(passiveUserFundId).get(0);

        // 确定账户正常
        if (activeUserFund == null) {
            return "付款账户异常";
        }else if(activeUserFund.getUserHealth() == 0) {
            return "付款账户信用等级异常";
        }
        if (passiveUserFund == null) {
            return "收款账户异常";
        }else if(passiveUserFund.getUserHealth() == 0) {
            return "收款账户信用等级异常";
        }

        // 确定账户类型
        String activeType = null;
        String passiveType = null;
        if (activeUserFund.getUserId() == 0 || activeUserFund.getGroupId() != 0) {
            activeType = String.valueOf(TypeOfUserOrGroup.GROUP);
        }else {
            activeType = String.valueOf(TypeOfUserOrGroup.USER);
        }
        if (passiveUserFund.getUserId() == 0 || passiveUserFund.getGroupId() != 0) {
            passiveType = String.valueOf(TypeOfUserOrGroup.GROUP);
        }else {
            passiveType = String.valueOf(TypeOfUserOrGroup.USER);
        }


        Connection conn = null;
        try {

            conn = ConnectionPoolManager.getConnection(); // 获取数据库连接
            conn.setAutoCommit(false); // 开始事务，设置自动提交为false

            // 使用悲观锁查询主动用户的基金账户
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `user_funds` WHERE `user_fund_id` = ? FOR UPDATE");
            ps.setLong(1, activeUserFundId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Active user fund not found.");
            }
            BigDecimal availableFunds = new BigDecimal(rs.getString("available_funds"));
            BigDecimal frozenFunds = new BigDecimal(rs.getString("frozen_funds"));
            BigDecimal fundAmount = new BigDecimal(fund);

            // 检查可用资金是否足够
            if (availableFunds.compareTo(fundAmount) < 0) {
                conn.rollback(); // 资金不足，回滚事务
                return "Insufficient funds";
            }

            // 更新可用资金和冻结资金
            availableFunds = availableFunds.subtract(fundAmount);
            frozenFunds = frozenFunds.add(fundAmount);
            ps = conn.prepareStatement("UPDATE `user_funds` SET `available_funds` = ?, `frozen_funds` = ? WHERE `user_fund_id` = ?");
            ps.setString(1, availableFunds.toPlainString());
            ps.setString(2, frozenFunds.toPlainString());
            ps.setLong(3, activeUserFundId);
            ps.executeUpdate();

            // 创建一个order对象
            Order order = new Order();
            // 类型
            order.setActiveType(activeType);
            // 重要
            order.setActiveId(activeUserFundId);
            order.setAmount(fund);
            // 类型
            order.setPassiveType(passiveType);
            // 重要
            order.setPassiveId(passiveUserFundId);
            order.setDescription(String.valueOf(FondAction.FONDGAVE));
            order.setChangeType(String.valueOf(FondAction.FONDGAVE));

            // 创建新订单并存入订单表中
            Long orderId = null;
            String sql = "INSERT INTO `orders` (`active_id`, `passive_id`, `amount`, `description`, `active_type`, `passive_type`, `gmt_create`, `gmt_modified`, `change_type`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? )";
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  // 指定返回生成的键
            ps.setLong(1, order.getActiveId());
            ps.setLong(2, order.getPassiveId());
            ps.setString(3, order.getAmount());
            ps.setString(4, order.getDescription());
            ps.setString(5, order.getActiveType());
            ps.setString(6, order.getPassiveType());
            ps.setString(7, order.getGmtCreate());
            ps.setString(8, order.getGmtModified());
            ps.setString(9, order.getChangeType());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();  // 获取生成的键
            if (rs.next()) {
                orderId = rs.getLong(1);  // 假设自动生成的键是一个长整型
            }

            conn.commit(); // 提交事务
            ConnectionPoolManager.releaseConnection(conn);
            return String.valueOf(orderId);
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback(); // 发生异常，回滚事务
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return "Transaction failed: " + ex.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 恢复自动提交
                    ConnectionPoolManager.releaseConnection(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 完整转账逻辑
     * @param activeUserFundId
     * @param passiveUserFundId
     * @param fund
     * @return
     */
    public String transferFunds(Long activeUserFundId, Long passiveUserFundId, String fund, Long acticeUserId) {
        UserFund userFund = DaoFactory.getUserFundDao().findByUserFundid(activeUserFundId).get(0);
        if(!userFund.getUserId().equals(acticeUserId)) {
            System.out.println(userFund);
            System.out.println(acticeUserId);
            System.out.println();
            return "您不具备该源账户的转账能力";
        }

        String formGaveStatus = formGave(activeUserFundId, passiveUserFundId, fund);
        if (formGaveStatus.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            return "您生成订单失败, 请稍后再试";
        }
        Long orderId = Long.valueOf(formGaveStatus);
        if (approveOrder(orderId).equals("Order approved and funds updated")) {
            return "Success";
        } else {
            return "订单创建完成, 转账失败, 可稍后从订单进入继续转账";
        }
    }

    /**
     * 完整转账逻辑
     * @param activeUserFundId
     * @param passiveUserFundId
     * @param fund
     * @return
     */
    public String transferFunds_group(Long activeUserFundId, Long passiveUserFundId, String fund, Long acticeUserId) {

        UserFund userFund = DaoFactory.getUserFundDao().findByUserFundid(activeUserFundId).get(0);
        if(!userFund.getUserId().equals(acticeUserId)) {
            return "您不具备该源账户的转账能力";
        }

        String formGaveStatus = formGave(activeUserFundId, passiveUserFundId, fund);
        if (formGaveStatus.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            return "您生成订单失败, 请稍后再试";
        }
        Long orderId = Long.valueOf(formGaveStatus);
        if (approveOrder(orderId).equals("Order approved and funds updated")) {
            return "Success";
        } else {
            return "订单创建完成, 转账失败, 可稍后从订单进入继续转账";
        }
    }

    /**
     * 查看我的订单
     * @param userId
     * @return
     */
    public String viewMyOrders(Long userId) {
        List<Order> ordersByUserId = DaoFactory.getOrderDao().findByPassiveId(userId);
        JSONArray jsonArray = new JSONArray();
        for (Order order : ordersByUserId) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("order_id", order.getOrderId());
            jsonObject.put("active_type", order.getActiveType());
            jsonObject.put("active_id", order.getActiveId());
            jsonObject.put("passive_type", order.getPassiveType());
            jsonObject.put("passive_id", order.getPassiveId());
            jsonObject.put("change_type", order.getChangeType());
            jsonObject.put("description", order.getDescription());
            jsonObject.put("amount", order.getAmount());
            jsonObject.put("gmt_create", order.getGmtCreate());
            jsonObject.put("gmt_modified", order.getGmtModified());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    /**
     * 对订单进行支付, 线程安全
     * @param orderId
     * @return
     */
    public String approveOrder(Long orderId) {
        Connection conn = null;
        try {
            conn = ConnectionPoolManager.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `orders` WHERE `order_id` = ? FOR UPDATE");
            ps.setLong(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                conn.rollback();
                return "Order not found";
            }
            String changeTyoe = rs.getString("change_type");
            if (!changeTyoe.equals(String.valueOf(FondAction.FONDGAVE))) {
                conn.rollback();
                return "Order is approved";
            }


            Long activeId = rs.getLong("active_id");
            Long passiveId = rs.getLong("passive_id");
            String amount = rs.getString("amount");
            TypeOfUserOrGroup activeType = TypeOfUserOrGroup.valueOf(rs.getString("active_type"));
            TypeOfUserOrGroup passiveType = TypeOfUserOrGroup.valueOf(rs.getString("passive_type"));


            BigDecimal totalFunds;
            BigDecimal available;
            BigDecimal frozen;

            // 处理主动方资金
            BigDecimal activeAmount = new BigDecimal(amount).negate();
            ps = conn.prepareStatement("SELECT * FROM `user_funds` WHERE `user_fund_id` = ? FOR UPDATE");
            ps.setLong(1, activeId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new SQLException("User fund not found");
            }
            totalFunds = new BigDecimal(rs.getString("total_funds"));
            available = new BigDecimal(rs.getString("available_funds"));
            frozen = new BigDecimal(rs.getString("frozen_funds"));
            totalFunds = totalFunds.add(activeAmount);
            frozen = frozen.add(activeAmount);
            ps = conn.prepareStatement("UPDATE `user_funds` SET `total_funds` = ?,`available_funds` = ?, `frozen_funds` = ?, `gmt_modified` = ? WHERE `user_fund_id` = ?");
            ps.setString(1, totalFunds.toPlainString());
            ps.setString(2, available.toPlainString());
            ps.setString(3, frozen.toPlainString());
            ps.setString(4, String.valueOf(LocalDateTime.now()));
            ps.setLong(5, activeId);
            ps.executeUpdate();

            // 处理被动方资金
            BigDecimal passiveAmount = new BigDecimal(amount);
            ps = conn.prepareStatement("SELECT * FROM `user_funds` WHERE `user_fund_id` = ? FOR UPDATE");
            ps.setLong(1, passiveId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new SQLException("User fund not found");
            }
            totalFunds = new BigDecimal(rs.getString("total_funds"));
            available = new BigDecimal(rs.getString("available_funds"));
            frozen = new BigDecimal(rs.getString("frozen_funds"));
            totalFunds = totalFunds.add(passiveAmount);
            available = available.add(passiveAmount);

            ps = conn.prepareStatement("UPDATE `user_funds` SET `total_funds` = ?,`available_funds` = ?, `frozen_funds` = ?, `gmt_modified` = ? WHERE `user_fund_id` = ?");
            ps.setString(1, totalFunds.toPlainString());
            ps.setString(2, available.toPlainString());
            ps.setString(3, frozen.toPlainString());
            ps.setString(4, String.valueOf(LocalDateTime.now()));
            ps.setLong(5, passiveId);
            ps.executeUpdate();


            // 更新订单
            ps = conn.prepareStatement("UPDATE `orders` SET `change_type` = ?, `gmt_modified` = ?, `description` = ? WHERE `order_id` = ?");
            ps.setLong(4, orderId);
            ps.setString(1, String.valueOf(FondAction.APPROVEORDER));
            ps.setString(2, String.valueOf(LocalDateTime.now()));
            ps.setString(3, String.valueOf(FondAction.APPROVEORDER));
            ps.executeUpdate();

            conn.commit();
            return "Order approved and funds updated";
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return "Transaction failed: " + ex.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    ConnectionPoolManager.releaseConnection(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 对订单拒绝支付, 线程安全
     * @param orderId
     * @return
     */
    public String rejectOrder(Long orderId) {
        Connection conn = null;
        try {
            conn = ConnectionPoolManager.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `orders` WHERE `order_id` = ? FOR UPDATE");
            ps.setLong(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                conn.rollback();
                return "Order not found";
            }

            Long activeId = rs.getLong("active_id");
            String amount = rs.getString("amount");

            // 解冻主动方的资金
            BigDecimal activeAmount = new BigDecimal(amount);

            ps = conn.prepareStatement("SELECT * FROM `user_funds` WHERE `user_fund_id` = ? FOR UPDATE");
            ps.setLong(1, activeId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new SQLException("User fund not found");
            }

            BigDecimal frozen = new BigDecimal(rs.getString("frozen_funds"));
            BigDecimal available = new BigDecimal(rs.getString("available_funds"));
            frozen = frozen.add(activeAmount.negate());
            available = available.add(activeAmount);

            ps = conn.prepareStatement("UPDATE `user_funds` SET `frozen_funds` = ?, `available_funds` = ? WHERE `user_fund_id` = ?");
            ps.setString(1, frozen.toPlainString());
            ps.setString(2, available.toPlainString());
            ps.setLong(3, activeId);
            ps.executeUpdate();

            // 标记订单状态为已拒绝
            ps = conn.prepareStatement("UPDATE `orders` SET `change_type` = ?, `gmt_modified` = ?, `description` = ? WHERE `order_id` = ?");
            ps.setLong(4, orderId);
            ps.setString(1, String.valueOf(FondAction.REJECTORDER));
            ps.setString(2, String.valueOf(LocalDateTime.now()));
            ps.setString(3, String.valueOf(FondAction.REJECTORDER));
            ps.executeUpdate();

            conn.commit();
            return "Order rejected and funds unfrozen";
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return "Transaction failed: " + ex.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    ConnectionPoolManager.releaseConnection(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
