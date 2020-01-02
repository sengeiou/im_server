package com.qingying0.aqachat.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.qingying0.aqachat.enums.MsgTypeEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 服务端处理所有接收消息的handler，这里只是示例，没有拆分太细，建议实际项目中按消息类型拆分到不同的handler中。
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class WebsocketRouterHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
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
        System.out.println("type" + type);
        System.out.println("data = " + msgJson.getJSONObject("data").getInteger("type"));
        switch (MsgTypeEnum.HEART.getByType(type)) {
            case HEART: //心跳
                break;
            case ONLINE: //上线
                JSONObject jsonData = msgJson.getJSONObject("data");
                long userId = jsonData.getLong("userId");
                int onlinetype = jsonData.getInteger("type");
                System.out.println(userId + "online");
                userChannel.put(userId, ctx.channel());

                break;
            case SENDMESSAGE: //发送消息
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
}
