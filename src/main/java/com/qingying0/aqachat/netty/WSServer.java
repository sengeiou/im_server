package com.qingying0.aqachat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class WSServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture future;

    @Autowired
    private WSServerInitialzer wsServerInitialzer;

    @Value("${netty.prot}")
    private int port;


    private static class SingletonWSServer {
        static final WSServer instance = new WSServer();
    }

    public static WSServer getInstance() {
        return SingletonWSServer.instance;
    }


//    @PostConstruct
    public void start() {
        System.out.println("port = " + port);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(wsServerInitialzer);
        try {
            this.future = serverBootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            log.error("websocket server start failed", e);
        }
    }

    public void close() {

    }
}
