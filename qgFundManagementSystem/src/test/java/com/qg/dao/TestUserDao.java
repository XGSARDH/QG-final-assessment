package com.qg.dao;

import com.qg.dao.impl.UserImpl;
import com.qg.po.User;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUserDao {
    @Test
    public void TestfindById() {
        UserDao userDao = new UserImpl();
        List<User> users = userDao.findById(1L);
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    @Test
    public void TestfindAll() {
        UserDao userDao = new UserImpl();
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    @Test
    public void TestSave() {
        try {
            UserDao userDao = new UserImpl();
            User user = new User("0", "0", "0", "0", "100", "0", LocalDateTime.now(), LocalDateTime.now());
            System.out.println(user.toString());
            Long userId = userDao.save(user);
            System.out.println(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getErrorCode());
        } finally {
        }
    }

    @Test
    public void TestUpdate() throws SQLException {
        User user = UserImpl.getInstance().findById(2L).get(0);
        user.setUserName("2");
        UserImpl.getInstance().update(user);
        user = UserImpl.getInstance().findById(2L).get(0);
        System.out.println(user.toString());
    }

    @Test
    public void TestDelete() throws SQLException {
        UserImpl.getInstance().delete(10L);
    }

    @Test
    public void TestFindByLikeUsername() {
        List<User> users = UserImpl.getInstance().findByLikeUsername("Willie Jenk");
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    @Test
    public void TestSoutDatetime() {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateStr = "2021-08-19 15:11:30";
        LocalDateTime date2 = LocalDateTime.parse(dateStr, fmt);
        System.out.println(date2);

        LocalDateTime localDateTime = LocalDateTime.now();
        dateStr = localDateTime.format(fmt);
        System.out.println(dateStr);
    }

    public static int matchRegex(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) {
            return 1; // 如果字符串符合正则表达式，返回1
        } else {
            return 0; // 如果字符串不符合正则表达式，返回0
        }
    }

    @Test
    public void testMatch() {
        String text = "-1.2";
        String regex = "^\\+?\\d+(\\.\\d+)?$";

        int result = matchRegex(text, regex);
        System.out.println(result); // 输出结果，1或0
    }

}
