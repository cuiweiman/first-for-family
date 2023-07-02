package com.first.family.client.hellodemo;

import com.first.family.grpc.api.message.HelloProto;
import com.first.family.grpc.api.message.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @description: 服务端流式响应——客户端
 * @author: cuiweiman
 * @date: 2023/6/30 11:54
 */
public class ServerStreamDemoClient {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        HelloProto.HelloRequest helloRequest = HelloProto.HelloRequest.newBuilder().setName("鲁智深").build();
        // 客户端阻塞获得 服务端流式信息
        // clientBlocked(helloRequest, managedChannel);

        // 客户端 异步监听 服务端流式信息
        clientAsyncListen(helloRequest, managedChannel);

    }

    /**
     * 客户端 异步监听 获取 服务端流式响应信息
     *
     * @param helloRequest   helloRequest
     * @param managedChannel 网络连接
     */
    public static void clientAsyncListen(HelloProto.HelloRequest helloRequest, ManagedChannel managedChannel) {
        try {
            HelloServiceGrpc.HelloServiceStub serviceStub = HelloServiceGrpc.newStub(managedChannel);
            serviceStub.streamService(helloRequest, new StreamObserver<>() {
                @Override
                public void onNext(HelloProto.HelloResponse helloResponse) {
                    // 服务端响应一个，客户端接收并处理一个
                    System.out.println("服务端流式响应::异步监听\t" + helloResponse.getResult());
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("服务端流式响应::异步监听错误\t" + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    // 服务端响应完成，长连接将关闭
                    System.out.println("服务端流式响应::客户端接收响应结束");
                }
            });

            System.out.println("异步执行确认");
            managedChannel.awaitTermination(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (!managedChannel.isShutdown()) {
                managedChannel.shutdown();
            }
        }
    }


    /**
     * 客户端 阻塞 获取 服务端流式响应信息
     *
     * @param helloRequest   helloRequest
     * @param managedChannel 网络连接
     */
    public static void clientBlocked(HelloProto.HelloRequest helloRequest, ManagedChannel managedChannel) {
        try {
            HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);

            // 服务端流式响应，响应结果是 迭代器 类型
            Iterator<HelloProto.HelloResponse> iterator = helloService.streamService(helloRequest);
            while (iterator.hasNext()) {
                HelloProto.HelloResponse helloResponse = iterator.next();
                System.out.println("服务端流式响应::客户端接收结果\t" + helloResponse.getResult());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (!managedChannel.isShutdown()) {
                managedChannel.shutdown();
            }
        }
    }


}
