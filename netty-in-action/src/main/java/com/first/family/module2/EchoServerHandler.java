package com.first.family.module2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @description: ChannelInboundHandlerAdapter 因为是简单的应用程序，所以继承以使用少量 的方法
 * @author: cuiweiman
 * @date: 2024/10/8 19:43
 * @see #channelRead(ChannelHandlerContext, Object)  消息传入后调用
 * @see #channelReadComplete(ChannelHandlerContext) 通知是 当前批量读取中的最后一条消息
 * @see #exceptionCaught(ChannelHandlerContext, Throwable) 读取操作中的异常处理
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        System.out.println("Server channelReadComplete ");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 打印异常并关闭 ChannelHandler 上下文
        cause.printStackTrace();
        ctx.close();
    }
}
