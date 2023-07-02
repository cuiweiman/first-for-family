package com.first.family.grpc.server.service.impl;

import com.first.family.grpc.api.message.HelloProto;
import com.first.family.grpc.api.message.HelloServiceGrpc;
import com.google.protobuf.ProtocolStringList;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * 一个 ServiceGrpc 只能继承 一次
 *
 * @description: gRPC 远程接口的服务实现
 * @author: cuiweiman
 * @date: 2023/6/29 09:47
 * @see #hello(HelloProto.HelloRequest, StreamObserver) 简单 Unary gRpc
 * @see #userService(HelloProto.UserRequest, StreamObserver)  简单 Unary gRpc
 * @see #streamService(HelloProto.HelloRequest, StreamObserver)   服务端流式响应 gRpc
 * @see #streamClient(StreamObserver) 客户端流式请求
 * @see #streamAll(StreamObserver)  客户端 和 服务端 双向流式请求和响应
 */
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    /**
     * 接收 client 提交的 请求 HelloRequest ，处理完成后提供响应信息 responseObserver
     * <p>
     * void 函数声明，表示不是通过常见的return响应的，而是通过 StreamObserver 流式响应
     * 与 gRPC 的通信方式相关
     *
     * @param request          request
     * @param responseObserver 观察者模式 流式响应，返回值信息。
     */
    @Override
    public void hello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String requestName = request.getName();
        // 业务代码
        System.out.println("[HelloServiceImpl] 请求参数:\t" + requestName);
        // 封装响应内容
        HelloProto.HelloResponse helloResponse = HelloProto.HelloResponse.newBuilder().setResult("success").build();
        // 完成内容响应
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void userService(HelloProto.UserRequest request, StreamObserver<HelloProto.UserResponse> responseObserver) {
        ProtocolStringList usernameList = request.getUsernameList();
        // 业务代码
        System.out.println("[HelloServiceImpl] 请求参数:\t" + usernameList);
        // 封装响应内容
        HelloProto.UserResponse userResponse = HelloProto.UserResponse.newBuilder().setResult("UserResponse").build();
        // 完成内容响应
        responseObserver.onNext(userResponse);
        // 处理完成，结束会话进程，否则http2长连接会保持
        responseObserver.onCompleted();
    }

    @Override
    public void streamService(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String name = request.getName();
        // 业务处理
        System.out.println("业务处理客户端的参数:\t" + name);
        // 封装响应信息
        HelloProto.HelloResponse.Builder builder = HelloProto.HelloResponse.newBuilder();
        for (int i = 0; i < 5; i++) {
            HelloProto.HelloResponse helloResponse = builder.setResult("服务端流式处理响应:\t" + i).build();
            responseObserver.onNext(helloResponse);
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // 服务端响应结束
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloProto.HelloRequest> streamClient(StreamObserver<HelloProto.HelloResponse> responseObserver) {

        // 异步 监听 并 接收 客户端的数据请求
        return new StreamObserver<>() {
            @Override
            public void onNext(HelloProto.HelloRequest helloRequest) {
                System.out.println("[监听客户端流式请求]\thelloRequest.name = " + helloRequest.getName());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("[监听客户端流式请求] 错误: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("[监听客户端流式请求] 完成");
                HelloProto.HelloResponse httpResponse =
                        HelloProto.HelloResponse.newBuilder().setResult("服务端接收数据完成").build();
                responseObserver.onNext(httpResponse);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<HelloProto.HelloRequest> streamAll(StreamObserver<HelloProto.HelloResponse> responseObserver) {

        // 异步 监听 并 接收 客户端的数据请求
        return new StreamObserver<>() {
            @Override
            public void onNext(HelloProto.HelloRequest helloRequest) {
                System.out.println("[监听客户端流式请求]\thelloRequest.name = " + helloRequest.getName());
                HelloProto.HelloResponse response =
                        HelloProto.HelloResponse.newBuilder().setResult("服务端接收:\t" + helloRequest.getName()).build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("[监听客户端流式请求] 错误: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("[监听客户端流式请求] 完成");
                HelloProto.HelloResponse responseComplete =
                        HelloProto.HelloResponse.newBuilder().setResult("服务端接收数据完成").build();
                responseObserver.onNext(responseComplete);
                responseObserver.onCompleted();
            }
        };
    }
}
