package com.qg.dao;

import com.qg.po.Group;

import java.sql.SQLException;
import java.util.List;

public interface GroupDao {

    List<Group> findByGroupId(Long groupId);

    // 根据创建者ID查找群组
    List<Group> findByCreateBy(Long createBy);

    // 查找所有群组
    List<Group> findAll();

    // 保存群组（新增或更新）
    Long save(Group group);

    // 更新群组信息
    void update(Group group);

    // 删除群组
    void delete(Long groupId);

    // 根据群组名模糊查找群组
    List<Group> findByLikeGroupName(String groupName);
}
