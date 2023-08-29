package com.first.shardingsphere.mapper;

import com.first.shardingsphere.ShardingSphereApp;
import com.first.shardingsphere.base.OrderEntity;
import com.first.shardingsphere.base.OrderItemEntity;
import com.first.shardingsphere.base.po.OrderPO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/**
 * application-tbrelate.yml
 *
 * @description: 关联表测试
 * @author: cuiweiman
 * @date: 2023/8/3 16:48
 */
@DisplayName("关联表测试")
@SpringBootTest(classes = ShardingSphereApp.class)
public class TbRelateTest {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderPoMapper orderPoMapper;

    @Test
    @DisplayName("关联表::入库")
    void insertOrderAndOrderItemTest() {
        for (int i = 0; i < 5; i++) {
            Long userId = 1L;
            OrderEntity order = OrderEntity.builder()
                    .orderNo("20230803_" + i).userId(userId).build();
            orderMapper.insert(order);

            for (int j = 0; j < 3; j++) {
                OrderItemEntity orderItem = OrderItemEntity.builder()
                        .orderNo("20230803_" + i).userId(userId).price(new BigDecimal(10)).number(2).build();
                orderItemMapper.insert(orderItem);
            }
        }

        for (int i = 5; i < 10; i++) {
            Long userId = 2L;
            OrderEntity order = OrderEntity.builder()
                    .orderNo("20230803_" + i).userId(userId).build();
            orderMapper.insert(order);

            for (int j = 0; j < 3; j++) {
                OrderItemEntity orderItem = OrderItemEntity.builder()
                        .orderNo("20230803_" + i).userId(userId).price(new BigDecimal(3)).number(3).build();
                orderItemMapper.insert(orderItem);
            }
        }
    }

    @Test
    @DisplayName("关联表::查询")
    void selectOrderAndOrderItemTest() {
        List<OrderPO> orderAmount = orderPoMapper.getOrderAmount();
        orderAmount.forEach(System.out::println);
    }


}
