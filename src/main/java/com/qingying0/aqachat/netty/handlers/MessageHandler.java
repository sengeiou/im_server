package com.qingying0.aqachat.netty.handlers;

import com.alibaba.fastjson.JSON;
import com.qingying0.aqachat.dto.MsgDTO;
import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.enums.MessageStatusEnum;
import com.qingying0.aqachat.enums.MsgTypeEnum;
import com.qingying0.aqachat.netty.UserChannelRelation;
import com.qingying0.aqachat.netty.handler.WebsocketRouterHandler;
import com.qingying0.aqachat.service.IMessageService;
import com.qingying0.aqachat.service.IRelationService;
import com.qingying0.aqachat.service.IUserService;
import com.qingying0.aqachat.utils.IdWorker;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageHandler {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IMessageService messageService;

    /**
     * 保存和发送message相关的消息
     * @param message
     */
    public void saveMessageAndSendMsg(Message message) {
        message.setId(idWorker.nextId());
        message.setCreateTime(new Date());
        message.setStatus(MessageStatusEnum.SEND.getStatus());
        messageService.saveMessage(message);
        sendAckMsg(message);
        sendMsg(message);

    }

    /**
     * 发送ackmessage的消息
     * @param message
     */
    public void sendAckMsg(Message message) {
        Channel channel = UserChannelRelation.getUserChannel(message.getSendId());
        if(channel == null) {
            System.out.println("sendId = " + message.getSendId() + "用户不在线");
        }
        if(channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(getAckMsgDTO(message)));
        }
    }

    public void sendMsg(Message message) {
        Channel channel = UserChannelRelation.getUserChannel(message.getTargetId());
        if(channel == null) {
            System.out.println("targetId = " + message.getSendId() + "用户不在线");
        }
        if(channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(getMsgDTO(message)));
        }
    }

    public String getMsgDTO(Message message) {
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setType(MsgTypeEnum.SENDMESSAGE.getType());
        msgDTO.setData(message);
        return JSON.toJSONString(msgDTO);
    }

    public String getAckMsgDTO(Message message) {
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setType(MsgTypeEnum.ACKMESSAGE.getType());
        msgDTO.setData(message);
        return JSON.toJSONString(msgDTO);
    }
}
