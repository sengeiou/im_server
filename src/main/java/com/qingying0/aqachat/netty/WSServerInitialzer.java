package com.qingying0.aqachat.netty;

import com.qingying0.aqachat.netty.handler.WebsocketRouterHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private WebsocketRouterHandler websocketRouterHandler;


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // websocket 基于http协议
        pipeline.addLast(new HttpServerCodec());

        pipeline.addLast(new ChunkedWriteHandler());

        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        //以上用于支持http协议

        //websocket服务器处理的协议，用于指定给客户端的路由 /ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //自定义handler
        pipeline.addLast(websocketRouterHandler);
    }
}
