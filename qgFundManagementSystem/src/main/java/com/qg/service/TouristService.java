package com.qg.service;

import com.qg.dao.UserDao;
import com.qg.dao.impl.UserImpl;
import com.qg.po.User;

public class TouristService {

    private static volatile TouristService instance;

    public static TouristService getInstance() {
        // 双重检查锁定，确保线程安全
        if (instance == null) {
            synchronized (TouristService.class) {
                if (instance == null) {
                    instance = new TouristService();
                }
            }
        }
        return instance;
    }

    public TouristService() {
    }

    /**
     * 注册
     */
    public void register() {

    }

    /**
     * 登录
     */
    public Integer login(String userName, String password) {
        System.out.println("userName: " + userName);
        System.out.println("password" + password);
        User user = UserImpl.getInstance().findByLikeUsername(userName).get(0);
        if(user == null) {
            return 0;
        } else if (!user.getPasswordHash().equals(password)) {
            return 0;
        }
        // equal is success
        return 1;
    }

    /**
     * 搜索公开群组
     */
    public void SearchGruop() {

    }

    /**
     * 查看公开群组
     */
    public void viewGroup() {

    }
}
