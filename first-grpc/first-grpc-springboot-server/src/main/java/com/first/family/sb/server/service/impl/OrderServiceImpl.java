package com.first.family.sb.server.service.impl;

import com.first.family.sb.api.domain.OrderRequest;
import com.first.family.sb.api.domain.OrderResponse;
import com.first.family.sb.api.domain.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/7/2 13:28
 */
@GrpcService
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    @Override
    public void order(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        String requestName = request.getName();
        // 业务代码
        System.out.println("[OrderServiceImpl] 请求参数:\t" + requestName);
        // 封装响应内容
        OrderResponse orderResponse = OrderResponse.newBuilder().setResult("success").build();
        // 完成内容响应
        responseObserver.onNext(orderResponse);
        responseObserver.onCompleted();
    }
}
