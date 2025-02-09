/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.transport;

import chuks.flatbook.fx.transport.message.crack.ChannelMessageDecoder;
import chuks.flatbook.fx.transport.message.crack.ChannelMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.ScheduledFuture;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author user
 */
public abstract class TransportClient extends Thread {

    private final String host;
    private final int port;
    private final EventLoopGroup group;
    private final SslContext sslContext; // Reuse the SSL context
    private Channel channel;  // Store the channel reference
    private final int maxInitialDelay = 5;  // Initial retry delay in seconds
    private final int maxBackoff = 60;      // Max backoff time in seconds
    private boolean onceEverConnected = false;
    private boolean connected;
    private int attempt;
    private ScheduledFuture<?> sheduleConn;

    public TransportClient(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
        this.group = new NioEventLoopGroup(); // Create the group at the start and reuse it
        this.sslContext = createSslContext(); // Create SSL context once
    }

    protected abstract SharableTransportHandler getHandler();

    protected abstract void onConnected();

    protected abstract void onDisconnected(String msg);

    @Override
    public void run() {
        connectToServer(); // Initial attempt with attempt count 0
    }

    public boolean isConnected() {
        return connected;
    }

    public int connectionAttempts() {
        return attempt;
    }

    private SslContext createSslContext() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        // Load keystore once, reuse it
        String keyStoreFileName = "mykeystore.jks";
        String keyStorePass = "Kachukwu123"; // TODO: Load the encrypted password from file

        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (FileInputStream trustStoreFile = new FileInputStream(keyStoreFileName)) {
            trustStore.load(trustStoreFile, keyStorePass.toCharArray());
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        return SslContextBuilder.forClient().trustManager(trustManagerFactory).build();
    }
    
    void onAttemptConnectFail(String message_prefix, Throwable cause){
          connected = false;
          System.err.println(cause == null? message_prefix : message_prefix +": " + cause.getMessage());
          retryConnection(); // Retry on connection failure
    }

    @SuppressWarnings("UseSpecificCatch")
    public void connectToServer() {

        if (connected) {
            if (sheduleConn != null) {
                sheduleConn.cancel(true);
            }            
            return;
        }

        try {
            System.out.println("Attempting to connect to the server... (Attempt " + (attempt + 1) + ")");

            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            SslHandler sslHandler = sslContext.newHandler(ch.alloc());
                            pipeline.addLast(sslHandler);

                            // Add your existing handlers
                            pipeline.addLast(new ChannelMessageDecoder());
                            pipeline.addLast(new ChannelMessageEncoder());
                            //pipeline.addLast(new AdminHandler((AccountContext) adminAccount));
                            pipeline.addLast(getHandler());

                        }
                    });

            // Try to connect and store channel reference
            
            ChannelFuture future = b.connect(host, port).sync();
            channel = future.channel(); // Store the channel for future management

            // Use one-time listener for successful connection
            future.addListener(f -> {
                if (f.isSuccess()) {
                    System.out.println("Connected");
                    onceEverConnected = true;
                    connected = true;
                    onConnected();
                    attempt = 0;
                } else {
                    onAttemptConnectFail("Connection failed",f.cause());
                }
            });

            // Use a listener to handle channel closure
            channel.closeFuture().addListener(f -> {
                onAttemptConnectFail("Connection closed. Checking connection again...",null);
            });

        } catch (Exception ex) {
            onAttemptConnectFail("Connection attempt failed",ex);
        }
    }

    private void retryConnection() {
        if (connected) {
            return;
        }
        attempt++;
        // Check if the channel is already closed and properly release resources
        if (channel != null && channel.isOpen()) {
            channel.close(); // Close the previous channel if it's still open
        }

        // Calculate exponential backoff delay, capped at maxBackoff
        int delay = Math.min(maxInitialDelay * (1 << attempt), maxBackoff);
        
        String strRetry = "Retrying connection in "
                + delay + " seconds...";
        String disconnMsg = onceEverConnected ? "Disconnected. " + strRetry
                : "Not Connected. " + strRetry;
        
        System.out.println(disconnMsg);
        
        onDisconnected(disconnMsg);
        // Schedule the reconnection attempt on the event loop
        sheduleConn = group.schedule(() -> connectToServer(), delay, TimeUnit.SECONDS);
    }

    public void shutdown() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close().sync(); // Close the channel gracefully
            }
            if (group != null && !group.isShutdown()) {
                group.shutdownGracefully();  // Shutdown event loop gracefully when stopping
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Shutdown interrupted: " + e.getMessage());
        }
        System.out.println("Client shutdown gracefully.");
    }
}
