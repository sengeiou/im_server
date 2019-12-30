package com.qingying0.aqachat.service;

import com.qingying0.aqachat.entity.Relation;

public interface IRelationService {
    Relation getByUserAndFriend(Long userId, Long friendId);

    void saveRelation(Long user1Id, Long user2Id);
}
