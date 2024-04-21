package com.qg.dao;

import com.qg.po.GroupMember;

import java.util.List;

public interface GroupMemberDao {
    List<GroupMember> findByMemberId(Long memberId);

    List<GroupMember> findByGroupId(Long groupId);

    List<GroupMember> findByUserId(Long userId);

    void save(GroupMember member);

    void update(GroupMember member);

    void delete(Long memberId);
}
