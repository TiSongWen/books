package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * ChannelInboundHandler 接口定义了作用于输入事件的方法
 * ChannelInboundHandlerAdapter 作为其子类
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        System.out.println("handler added " + ctx.channel().isWritable());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        System.out.println("handlerRemoved " + ctx.channel().isWritable());
    }

    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception { // (4)
        System.out.println("handlerRemoved " + ctx.channel().isWritable());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8) + " " + ctx.channel().isWritable());
       // ctx.write(Unpooled.copiedBuffer("Ack", CharsetUtil.UTF_8));
       // ctx.flush();
        // ByteBuf byteBuf

        Channel channel = ctx.channel();


        System.out.println(channel.isWritable());
        ChannelFuture result = ctx.writeAndFlush(Unpooled.copiedBuffer("Ack\r\n", CharsetUtil.UTF_8));
        try {
            result.sync();
            if (!result.isSuccess()) {
                System.out.println("Send failed: " + result.cause());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(channel.isWritable());

        Scanner scanner = new Scanner(System.in);

        scanner.nextInt();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线 " + ctx.channel().isWritable());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       // ctx.write("testtttttttttt");
        ctx.flush();
       // ctx.channel().writeAndFlush("test");
        System.out.println("channelReadComplete"  + ctx.channel().isWritable());
        ctx.channel().write("test2");
        ctx.channel().flush();
    }


    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelWritabilityChanged");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"掉线");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
