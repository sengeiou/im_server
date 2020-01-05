package com.qingying0.aqachat.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.enums.MsgTypeEnum;
import com.qingying0.aqachat.netty.UserChannelRelation;
import com.qingying0.aqachat.netty.handlers.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端处理所有接收消息的handler，这里只是示例，没有拆分太细，建议实际项目中按消息类型拆分到不同的handler中。
 */
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
                }

//                if(onlinetype == 1) {
//                    log.info("userId = " + userId + ":下线");
//                    userChannel.remove(userId);
//                }

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
            case SESSION: //会话相关
                break;
            case REQUEST: //请求相关
                break;
            default:
                break;
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel register");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregister");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unactive");
        super.channelInactive(ctx);
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channel read finish");
//        super.channelReadComplete(ctx);
//    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("用户事件触发");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("可写更改");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
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
