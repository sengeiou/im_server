package com.qingying0.aqachat.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.enums.MsgTypeEnum;
import com.qingying0.aqachat.netty.handlers.FriendHandler;
import com.qingying0.aqachat.netty.handlers.MessageHandler;
import com.qingying0.aqachat.netty.handlers.MsgHandler;
import com.qingying0.aqachat.netty.handlers.SessionHandler;
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

    private static final ConcurrentHashMap<Long, Channel> userChannel = new ConcurrentHashMap<>(15000);
    private static ChannelGroup clients = new DefaultChannelGroup(null);
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        clients.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        JSONObject msgJson = JSONObject.parseObject(msg);
        Integer type = msgJson.getInteger("type");
        System.out.println(msg);
        switch (MsgTypeEnum.HEART.getByType(type)) {
            case HEART: //心跳
                break;
            case ONLINE: //上线
                JSONObject onlineData = msgJson.getJSONObject("data");
                Long userId = onlineData.getLong("userId");
                int onlinetype = onlineData.getInteger("type");
                if(onlinetype == 0) {
                    log.info("userId = " + userId + ":上线");
                    userChannel.put(userId, ctx.channel());
                }
                if(onlinetype == 1) {
                    log.info("userId = " + userId + ":下线");
                    userChannel.remove(userId);
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
                message.setSendId(sessionId);
                message.setType(sendType);
                message.setContent(content);
                message.setTargetId(targetId);
//                messageHandler.saveMessageAndSendMsg();
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

    public Channel getChannelByUserId(Long userId) {
        return userChannel.get(userId);
    }


}
