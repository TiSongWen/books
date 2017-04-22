package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * EventLoopGroup
 * NioServerSocketChannel
 * Handler
 * Pipeline
 */
public class EchoServer {
    private final int port = 8080;

    public EchoServer(int port) {

    }

    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();

        // 指定NioEventLoopGroup接收和处理新的连接(处理Event)
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup group2 = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group,group2).channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel作为Channel类型
                    .localAddress(new InetSocketAddress(port))
                    // 当一个新的连接被接收时，一个新的子Channel会被创建, 然后这个ChannelInitializer会把 ServerHandler实例添加到
                    // 该Channel的ChannelPipeline中, 该Handler会接收有关输入消息的通知
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("init channel");
                            ch.pipeline().addLast(serverHandler);
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public void run() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("init channel");
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            System.out.println("SimpleChatServer 启动了");

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync(); // (7)

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            System.out.println("SimpleChatServer 关闭了");
        }
    }

    public static void main(String[] args) {

        try {
            new EchoServer(0).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
