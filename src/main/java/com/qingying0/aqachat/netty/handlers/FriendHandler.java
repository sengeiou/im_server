package com.qingying0.aqachat.netty.handlers;

import com.alibaba.fastjson.JSON;
import com.qingying0.aqachat.dto.FriendMsgDTO;
import com.qingying0.aqachat.dto.MsgDTO;
import com.qingying0.aqachat.enums.MsgTypeEnum;
import com.qingying0.aqachat.netty.UserChannelRelation;
import com.qingying0.aqachat.service.IRelationService;
import com.qingying0.aqachat.service.IUserService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FriendHandler {

    @Autowired
    private IRelationService relationService;

    @Autowired
    private IUserService userService;


    public void getFriendRequestSucMsg(Long sendId, Long targetId) {
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setType(MsgTypeEnum.FRIEND.getType());
        FriendMsgDTO friendMsgDTO = new FriendMsgDTO();
//        friendMsgDTO.setType(FriendMsgType.ADD_FRIEND_SUCCESS.getType());
//        friendMsgDTO.setData(userService.getUserById(sendId));
//        msgDTO.setData(friendMsgDTO);
//        JSON.toJSONString(msgDTO);
//        Channel channel = UserChannelRelation.getUserChannel(targetId);
    }
}
