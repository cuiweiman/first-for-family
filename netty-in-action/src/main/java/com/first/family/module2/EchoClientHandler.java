package com.first.family.module2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * 客户端集成了 {@link SimpleChannelInboundHandler} 原因：
 * 当 channelRead0 方法执行完成时，已经有传入了消息，并且已经处理完了。
 * 当 该方法返回时，SimpleChannelInboundHandler 负责释放 存储消息的 ByteBuf 的内存引用。
 *
 * @description: SimpleChannelInboundHandler
 * @author: cuiweiman
 * @date: 2024/10/8 19:59
 * @see #channelActive(ChannelHandlerContext) 建立连接时触发
 * @see #channelRead0(ChannelHandlerContext, ByteBuf) 接收消息时触发
 * @see #exceptionCaught(ChannelHandlerContext, Throwable) 发生异常时触发
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", StandardCharsets.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("Client received: " + byteBuf.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
