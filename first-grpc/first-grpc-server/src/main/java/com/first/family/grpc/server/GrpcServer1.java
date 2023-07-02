package com.first.family.grpc.server;

import com.first.family.grpc.api.message.HelloServiceGrpc;
import com.first.family.grpc.future.message.FutureStubServiceGrpc;
import com.first.family.grpc.server.futuredemo.FutureStubServiceImpl;
import com.first.family.grpc.server.service.impl.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.netty.shaded.io.netty.util.concurrent.DefaultThreadFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: rpc 服务端启动
 * @author: cuiweiman
 * @date: 2023/6/29 10:02
 */
public class GrpcServer1 {
    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(10, 30,
                1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(2000),
                new DefaultThreadFactory("grpc-server"), new ThreadPoolExecutor.CallerRunsPolicy());

        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(9000)
                // 自定义线程池
                .executor(executor)
                // 每隔KeepAliveTime时间，发送PING帧测量最小往返时间，确定空闲连接是否仍然有效
                .keepAliveTime(10, TimeUnit.SECONDS)
                // 超过KeepAliveTimeout，关闭连接，我们设置为3S
                .keepAliveTimeout(3, TimeUnit.SECONDS);

        List<ServerServiceDefinition> serviceDefinitionList = List.of(
                // 添加 RPC Service 服务: 简单rpc
                HelloServiceGrpc.bindService(new HelloServiceImpl()),
                // 添加 RPC Service 服务: 客户端以 FutureStub 方式监听服务端消息，只支持 一元RPC
                FutureStubServiceGrpc.bindService(new FutureStubServiceImpl())
        );
        serverBuilder.addServices(serviceDefinitionList);
        Server server = serverBuilder.build();
        try {
            server.start();
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
