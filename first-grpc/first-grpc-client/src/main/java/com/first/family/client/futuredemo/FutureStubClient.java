package com.first.family.client.futuredemo;

import com.first.family.grpc.future.message.FutureStubDemoProto;
import com.first.family.grpc.future.message.FutureStubServiceGrpc;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: FutureStub 代理方式测试，同时支持同步和异步，但是仅支持 Unary gRpc
 * @author: cuiweiman
 * @date: 2023/6/30 14:41
 */
public class FutureStubClient {
    public static void main(String[] args) {
        // futureDemoSync();
        futureDemoAsync();
    }

    public static void futureDemoAsync() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
                1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                new DefaultThreadFactory("future"), new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            FutureStubServiceGrpc.FutureStubServiceFutureStub futureStub = FutureStubServiceGrpc.newFutureStub(managedChannel);
            FutureStubDemoProto.FutureStubReq futureStubReq = FutureStubDemoProto.FutureStubReq.newBuilder().setName("FutureSyncDemo").build();
            ListenableFuture<FutureStubDemoProto.FutureStubResp> listenableFuture = futureStub.futureStubTest(futureStubReq);
            Futures.addCallback(listenableFuture, new FutureCallback<>() {
                @Override
                public void onSuccess(FutureStubDemoProto.FutureStubResp result) {
                    System.out.println(Thread.currentThread().getName() + "\t异步接收响应信息\t" + result.getResult());
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println("异步响应错误\t" + t.getMessage());
                }
            }, executor);

            System.out.println("异步逻辑处理结束");
            managedChannel.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println("managedChannel等待结束");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
            if (!executor.isShutdown() && !executor.isTerminating() && !executor.isTerminated()) {
                executor.shutdownNow();
            }
        }
    }

    public static void futureDemoSync() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        try {
            FutureStubServiceGrpc.FutureStubServiceFutureStub futureStub = FutureStubServiceGrpc.newFutureStub(managedChannel);
            FutureStubDemoProto.FutureStubReq futureStubReq = FutureStubDemoProto.FutureStubReq.newBuilder().setName("FutureSyncDemo").build();
            ListenableFuture<FutureStubDemoProto.FutureStubResp> listenableFuture = futureStub.futureStubTest(futureStubReq);
            FutureStubDemoProto.FutureStubResp resp = listenableFuture.get();
            System.out.println("同步接收响应信息\t" + resp.getResult());
            System.out.println("同步逻辑处理结束");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
        }
    }
}
