/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.transport;

import chuks.flatbook.fx.transport.message.crack.ChannelMessageDecoder;
import chuks.flatbook.fx.transport.message.crack.ChannelMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;

/**
 *
 * @author user
 */
public abstract class TransportServer {

    private final int port;
    

    public TransportServer(int port) {
        this.port = port;
    }
    
    protected abstract DynamicIpFilter getIpFilter();
    
    protected abstract SharableTransportHandler getHandler();

    public void run() throws Exception {
        
        String keyStoreFileName = "mykeystore.jks";
        String keyStorePass = "Kachukwu123";//TODO -  Load the ecrypted password from file
        
        
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyStoreFile = new FileInputStream(keyStoreFileName)) {
            keyStore.load(keyStoreFile, keyStorePass.toCharArray());
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePass.toCharArray());

        SslContext sslContext = SslContextBuilder
                .forServer(keyManagerFactory)
                .build();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            // Add SSL handler to the pipeline
                            pipeline.addLast(sslContext.newHandler(ch.alloc()));
                           
                            //Next add the ip filter
                            ch.pipeline().addLast(getIpFilter());

                            // Add your existing handlers
                            pipeline.addLast(new ChannelMessageDecoder());
                            pipeline.addLast(new ChannelMessageEncoder());
                            pipeline.addLast(getHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            b.bind(port).sync().channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
