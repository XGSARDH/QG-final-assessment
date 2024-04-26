package com.qg.dao;

import com.qg.po.GroupMember;

import java.sql.SQLException;
import java.util.List;

public interface GroupMemberDao {
    List<GroupMember> findByMemberId(Long memberId);

    List<GroupMember> findByGroupId(Long groupId);

    List<GroupMember> findAll();

    List<GroupMember> findByUserId(Long userId);

    void save(GroupMember member) throws SQLException ;

    void update(GroupMember member) throws SQLException;

    void delete(Long memberId) throws SQLException;
}
