package com.qg.dao;

import com.qg.dao.impl.GroupImpl;
import com.qg.po.Group;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class TestGroupDao {

    @Test
    public void TestfindByGroupId() {
        List<Group> groups = GroupImpl.getInstance().findByGroupId(1L);
        for (Group group : groups) {
            System.out.println(group);
        }
    }

    @Test
    public void TestfindByCreateBy() {
        List<Group> groups = GroupImpl.getInstance().findByCreateBy(1L);
        for (Group group : groups) {
            System.out.println(group);
        }
    }

    @Test
    public void TestfindAll() {
        List<Group> groups = GroupImpl.getInstance().findAll();
        for (Group group : groups) {
            System.out.println(group);
        }
    }

    @Test
    public void TestSave() throws SQLException {
        Group group = GroupImpl.getInstance().findAll().get(0);

        System.out.println(group.toString());
        Long groupId = GroupImpl.getInstance().save(group);
        System.out.println(groupId);
    }

    @Test
    public void TestUpdate() throws SQLException {
        Group group = GroupImpl.getInstance().findByGroupId(1L).get(0);
        group.setGroupId(1L);
        group.setGroupName("2");
        GroupImpl.getInstance().update(group);
        group = GroupImpl.getInstance().findByGroupId(1L).get(0);
        System.out.println(group.toString());
    }

    @Test
    public void TestDelete() throws SQLException {
        GroupImpl.getInstance().delete(2L);
    }

    @Test
    public void TestFindByLikeUsername() {
        List<Group> groups = GroupImpl.getInstance().findByLikeGroupName("Willie Jenk");
        for (Group group : groups) {
            System.out.println(group.toString());
        }
    }
}
