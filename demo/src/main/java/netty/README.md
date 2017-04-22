# Netty 核心组件

1. Channels
2. Callbacks
3. Futures
4. Events
5. Handler

## Channels

Channel是Java NIO的抽象。代表：一个连接（Socket连接，文件，硬件设备）

## Callbacks

当一个Callback 被触发，事件可以被 ChannelHandler的接口处理

ChannelHandler被Callback 触发
```
public class ConnectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 当一个连接建立时，该函数被调用
    }
}
```

## Future(与ChannelFutureListener)

JDK的Future只提供了：检测功能函数是否完成，否则阻塞直到操作完成。所以Netty提供了自己的 
ChannelFuture实现，用来异步操作。

ChannelFuture提供额外方法：注册一个或多个ChannelFutureListener实例，这些监听者的回调方法`operationComplete()`在操作完成时被调用。

而每个Netty的IO操作都会返回一个 ChannelFuture ，即没有一个操作是阻塞的。（从上之下都是异步与事件驱动完成的）

### 异步连接

```
Channel channel = ...

// 异步连接
ChannelFuture future = channel.connect(new InetSocketAddress(address, port));

future.addListener(new ChannelFutureListener() {
    @Override
    public void operationComplete(ChannelFuture future) {
        if (future.isSuccess() {
            // 操作成功，创建ByteBuf保存数据
            ByteBuf buffer = Unpooled.copiedBuffer("", Charset.defaultCharset);
            // 异步发送数据到远程端，返回一个ChannelFuture
            ChannelFuture wf = future.channel().writeAndFlush(buffer); 
        } else {
            Throwable cause = future.cause();
            cause.printStackTrace();
        }
    }
});
```

> 初步总结：callback 与 Future(ChannelFutureListener) 保证了netty的异步与事件驱动

## Event与Handler

Event ： 事件
Handler：响应的Callback

# Netty 组件与设计

1. Channel
2. EventLoop : 控制流，多线程，并发
3. ChannelFuture : 异步通知

## Channel

封装了Socket的IO操作

## EventLoop

处理一个连接的Event。

1. 创建Channel
2. Channel注册到EventLoop上
3. 


## ChannelFuture

