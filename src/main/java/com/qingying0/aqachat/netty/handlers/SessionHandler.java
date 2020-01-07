package com.qingying0.aqachat.netty.handlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingying0.aqachat.dto.MsgDTO;
import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.enums.MsgTypeEnum;
import com.qingying0.aqachat.netty.UserChannelRelation;
import com.qingying0.aqachat.service.IMessageService;
import com.qingying0.aqachat.service.IUserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionHandler {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IUserSessionService userSessionService;


    public void updateSessionByOpenSession(Long sessionId, Long userId) {
        System.out.println("更新session num 0: sessionId = " + sessionId + "|" + userId);
        userSessionService.updateUserUnreadnumZero(sessionId, userId);
    }


    public void updateSessionBySendMessage(Message message) {
        System.out.println("更新session: sessionId = " + message.getSessionId() + "|" + message.getTargetId());
        userSessionService.updateSessionBySendMessage(message);
    }
}
