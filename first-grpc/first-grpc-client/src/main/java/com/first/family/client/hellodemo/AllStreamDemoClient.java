package com.first.family.client.hellodemo;

import com.first.family.grpc.api.message.HelloProto;
import com.first.family.grpc.api.message.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/6/30 16:02
 * @see #allStreamDemo()  服务端 客户端 双向流式 请求和响应
 */
public class AllStreamDemoClient {
    public static void main(String[] args) {
        allStreamDemo();
    }

    /**
     * 服务端 客户端 双向流式 请求和响应
     */
    public static void allStreamDemo() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        try {
            HelloServiceGrpc.HelloServiceStub helloServiceStub = HelloServiceGrpc.newStub(managedChannel);
            HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();
            // 异步监听 服务端的响应信息
            StreamObserver<HelloProto.HelloRequest> streamClient = helloServiceStub.streamAll(new StreamObserver<>() {
                @Override
                public void onNext(HelloProto.HelloResponse helloResponse) {
                    System.out.println("[监听服务端响应]\thelloResponse.result = " + helloResponse.getResult());
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("[监听服务端响应] 错误 " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("[监听服务端响应]\t 结束 ");
                }
            });

            // 发送客户端 流式请求
            for (int i = 0; i < 10; i++) {
                HelloProto.HelloRequest helloRequest = builder.setName("name-" + i).build();
                streamClient.onNext(helloRequest);
            }
            streamClient.onCompleted();
            managedChannel.awaitTermination(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
        }
    }
}
