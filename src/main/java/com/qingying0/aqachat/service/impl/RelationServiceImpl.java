package com.qingying0.aqachat.service.impl;

import com.qingying0.aqachat.component.HostHolder;
import com.qingying0.aqachat.dto.FriendDTO;
import com.qingying0.aqachat.entity.Relation;
import com.qingying0.aqachat.mapper.RelationMapper;
import com.qingying0.aqachat.netty.UserChannelRelation;
import com.qingying0.aqachat.netty.handler.WebsocketRouterHandler;
import com.qingying0.aqachat.netty.handlers.FriendHandler;
import com.qingying0.aqachat.service.IRelationService;
import com.qingying0.aqachat.utils.IdWorker;
import com.qingying0.aqachat.utils.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 好友关系表
 */
@Service
public class RelationServiceImpl implements IRelationService {

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private FriendHandler friendHandler;


    /**
     * 查找用户和好友的关系是否存在
     * @param userId
     * @param friendId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Relation getByUserAndFriend(Long userId, Long friendId) {
        Relation relation = relationMapper.selectByUserAndFriend(userId, friendId);
        return relation;
    }

    /**
     * 保存好友关系，保存两条
     * @param user1Id
     * @param user2Id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveRelation(Long user1Id, Long user2Id) {
        Relation relation1 = new Relation();
        relation1.setId(idWorker.nextId());
        relation1.setUserId(user1Id);
        relation1.setFriendId(user2Id);
        relation1.setStatus(SystemConst.RELATION_FRIEND);
        Relation relation2 = new Relation();
        relation2.setId(idWorker.nextId());
        relation2.setUserId(user2Id);
        relation2.setFriendId(user1Id);
        relation2.setStatus(SystemConst.RELATION_FRIEND);
        relationMapper.insert(relation1);
        relationMapper.insert(relation2);

    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendDTO> getFriends() {
        List<FriendDTO> friendDTOS = relationMapper.selectByUserId(hostHolder.getUserId());
        for(FriendDTO friendDTO : friendDTOS) {
            if(UserChannelRelation.getUserChannel(friendDTO.getId()) != null) {
                friendDTO.setStatus(SystemConst.FRIEND_ONLINE);
            } else {
                friendDTO.setStatus(SystemConst.FRIEND_OFFLINE);
            }
        }
        return friendDTOS;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendDTO> getFriendsByUserId(Long sendId) {
        return relationMapper.selectByUserId(hostHolder.getUserId());
    }
}
