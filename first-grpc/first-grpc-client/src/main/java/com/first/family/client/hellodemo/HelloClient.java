package com.first.family.client.hellodemo;

import com.first.family.grpc.api.message.HelloProto;
import com.first.family.grpc.api.message.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @description: gRPC 客户端，像调用 本地方法一样，实现远程调用
 * @author: cuiweiman
 * @date: 2023/6/29 10:12
 */
public class HelloClient {
    public static void main(String[] args) {
        callHelloService();
        // callUserService();
    }

    public static void callHelloService() {
        // 1. 创建 Grpc 网络通信管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();

        try {
            // 2. 获取代理对象 stub: BlockingStub 发送请求后，阻塞，等待响应信息
            HelloServiceGrpc.HelloServiceBlockingStub blockingStub = HelloServiceGrpc.newBlockingStub(managedChannel);

            HelloProto.HelloRequest helloRequest = HelloProto.HelloRequest.newBuilder().setName("我是测试").build();
            HelloProto.HelloResponse helloResponse = blockingStub.hello(helloRequest);

            System.out.println(helloResponse.getResult());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
        }
    }

    public static void callUserService() {
        // 1. 创建 Grpc 网络通信管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        try {
            // 2. 获取代理对象 stub: BlockingStub 发送请求后，阻塞，等待响应信息
            HelloServiceGrpc.HelloServiceBlockingStub blockingStub = HelloServiceGrpc.newBlockingStub(managedChannel);
            HelloProto.UserRequest userRequest = HelloProto.UserRequest.newBuilder()
                    .addUsername("Jack").addUsername("Rose").addUsername("Harry").addUsername("Ginny").build();
            HelloProto.UserResponse userResponse = blockingStub.userService(userRequest);
            System.out.println(userResponse.getResult());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
        }
    }


}
