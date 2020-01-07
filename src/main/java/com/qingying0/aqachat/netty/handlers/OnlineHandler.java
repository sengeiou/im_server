package com.qingying0.aqachat.netty.handlers;

import com.alibaba.fastjson.JSON;
import com.qingying0.aqachat.dto.AckDTO;
import com.qingying0.aqachat.dto.FriendDTO;
import com.qingying0.aqachat.dto.MsgDTO;
import com.qingying0.aqachat.enums.AckTypeEnum;
import com.qingying0.aqachat.enums.MsgTypeEnum;
import com.qingying0.aqachat.netty.UserChannelRelation;
import com.qingying0.aqachat.service.IRelationService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OnlineHandler {

    @Autowired
    private IRelationService relationService;

    public void sendOnlineMsg(Long userId) {
        List<FriendDTO> friends = relationService.getFriendsByUserId(userId);
        String onlineMsg = getSendOnlineMsg(userId);
        for(FriendDTO friendDTO : friends) {
            Channel channel = UserChannelRelation.getUserChannel(friendDTO.getId());
            if(channel != null) {
                channel.writeAndFlush(new TextWebSocketFrame(onlineMsg));
            }
        }
    }

    public String getSendOnlineMsg(Long userId) {
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setType(MsgTypeEnum.ONLINE.getType());
        msgDTO.setData(userId);
        return JSON.toJSONString(msgDTO);
    }
}
