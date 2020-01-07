package com.qingying0.aqachat.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.enums.MsgTypeEnum;
import com.qingying0.aqachat.netty.UserChannelRelation;
import com.qingying0.aqachat.netty.handlers.*;
import com.qingying0.aqachat.service.IMessageService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
@Slf4j
public class WebsocketRouterHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private SessionHandler sessionHandler;

    @Autowired
    private MsgHandler msgHandler;

    @Autowired
    private FriendHandler friendHandler;

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private AckHandler ackHandler;

    @Autowired
    private IMessageService messageService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        JSONObject msgJson = JSONObject.parseObject(msg);
        Integer type = msgJson.getInteger("type");
        System.out.println("----------------------------------------------");
        System.out.println(msg);
        switch (MsgTypeEnum.HEART.getByType(type)) {
            case HEART: //心跳
                ctx.channel().writeAndFlush(new TextWebSocketFrame(ackHandler.getAckHeart()));
                break;
            case ONLINE: //上线
                JSONObject onlineData = msgJson.getJSONObject("data");
                Long userId = onlineData.getLong("userId");
                int onlinetype = onlineData.getInteger("type");
                if(onlinetype == 0) {
                    log.info("userId = " + userId + ":上线");
                    UserChannelRelation.setUserChannel(userId, ctx.channel());
                    UserChannelRelation.setChannelUserId(ctx.channel(), userId);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(ackHandler.getAckOnlineMsg()));
                    messageHandler.sendUnreceivedMessage(userId);
                }
                if(onlinetype == 1) {
                    log.info("userId = " + userId + ":下线");
                    Long logoutId = UserChannelRelation.getChannelUserId(ctx.channel());
                    if(userId != null) {
                        UserChannelRelation.removeUserChannel(userId);
                        log.info("用户 :" + logoutId + "下线");
                    }
                    UserChannelRelation.removeChannelUserId(ctx.channel());
                }
                break;
            case SENDMESSAGE: //发送消息
                JSONObject sendMessageData = msgJson.getJSONObject("data");
                Long sendId = sendMessageData.getLong("sendId");
                Long sessionId = sendMessageData.getLong("sessionId");
                Long targetId = sendMessageData.getLong("targetId");
                Integer sendType = sendMessageData.getInteger("type");
                String content = sendMessageData.getString("content");
                System.out.println(sendMessageData.toJSONString());
                Message message = new Message();
                message.setSendId(sendId);
                message.setType(sendType);
                message.setContent(content);
                message.setTargetId(targetId);
                message.setSessionId(sessionId);
                messageHandler.saveMessageAndSendMsg(message);
                break;
            case FRIEND: //好友相关
                break;
            case SESSION:
                JSONObject sessionData = msgJson.getJSONObject("data");
                System.out.println(sessionData.toJSONString());
                Integer sessionType = sessionData.getInteger("type");
                if(sessionType.equals(new Integer(0))) {
                    System.out.println("点开session id = " + sessionData.getLong("data"));
                    sessionHandler.updateSessionByOpenSession(sessionData.getLong("data"), UserChannelRelation.getChannelUserId(ctx.channel()));
                }
                break;
            case REQUEST: //请求相关
                break;
            case ACKMESSAGE:
                break;
            case ACK:
                JSONObject ackMessageData = msgJson.getJSONObject("data");
                int ackType = ackMessageData.getIntValue("type");
                System.out.println("ack = " + ackMessageData.toJSONString());
                System.out.println("acttype = " + ackType);
                if(ackType == 0) {
                    System.out.println("delete" + ackMessageData.getLong("id"));
                    Long messageId = ackMessageData.getLong("id");
                    messageService.deleteMessageById(messageId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("remove handler");
        Long userId = UserChannelRelation.getChannelUserId(ctx.channel());
        if(userId != null) {
            UserChannelRelation.removeUserChannel(userId);
            log.info("用户:" + userId + "下线");
        }
        UserChannelRelation.removeChannelUserId(ctx.channel());
        super.handlerRemoved(ctx);
    }
}
