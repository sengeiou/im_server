package com.qingying0.aqachat.netty.handlers;

import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.netty.handler.WebsocketRouterHandler;
import com.qingying0.aqachat.service.IRelationService;
import com.qingying0.aqachat.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {



    @Autowired
    private WebsocketRouterHandler websocketRouterHandler;


}
