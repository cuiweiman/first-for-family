package com.first.family.grpc.server.futuredemo;

import com.first.family.grpc.future.message.FutureStubDemoProto;
import com.first.family.grpc.future.message.FutureStubServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @description: FutureStub 代理方式测试
 * @author: cuiweiman
 * @date: 2023/6/30 14:39
 */
public class FutureStubServiceImpl extends FutureStubServiceGrpc.FutureStubServiceImplBase {

    @Override
    public void futureStubTest(FutureStubDemoProto.FutureStubReq request, StreamObserver<FutureStubDemoProto.FutureStubResp> responseObserver) {
        String requestName = request.getName();
        // 业务代码
        System.out.println("[FutureStubServiceImpl] 请求参数:\t" + requestName);
        // 封装响应内容
        FutureStubDemoProto.FutureStubResp resp = FutureStubDemoProto.FutureStubResp.newBuilder().setResult("success").build();
        // 完成内容响应
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
