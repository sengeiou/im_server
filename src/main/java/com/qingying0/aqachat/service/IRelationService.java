package com.qingying0.aqachat.service;

import com.qingying0.aqachat.dto.FriendDTO;
import com.qingying0.aqachat.entity.Relation;

import java.util.List;

public interface IRelationService {
    Relation getByUserAndFriend(Long userId, Long friendId);

    void saveRelation(Long user1Id, Long user2Id);

    List<FriendDTO> getFriends();

    List<FriendDTO> getFriendsByUserId(Long sendId);
}
