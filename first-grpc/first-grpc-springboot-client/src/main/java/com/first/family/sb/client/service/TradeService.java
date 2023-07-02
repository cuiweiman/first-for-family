package com.first.family.sb.client.service;

import com.first.family.sb.api.domain.OrderRequest;
import com.first.family.sb.api.domain.OrderResponse;
import com.first.family.sb.api.domain.OrderServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/7/2 14:04
 */
@Service
public class TradeService {

    @GrpcClient("order-server")
    private OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

    public void callOrder() {
        OrderRequest orderRequest = OrderRequest.newBuilder().setName("客户端请求参数").build();
        OrderResponse orderResponse = orderServiceBlockingStub.order(orderRequest);
        System.out.println(orderResponse.getResult());
    }

}
