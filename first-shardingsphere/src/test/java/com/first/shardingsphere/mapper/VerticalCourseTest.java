package com.first.shardingsphere.mapper;

import com.first.shardingsphere.ShardingSphereApp;
import com.first.shardingsphere.base.OrderEntity;
import com.first.shardingsphere.base.UserEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * application-vertical.yml
 *
 * @description: 垂直分片测试
 * @author: cuiweiman
 * @date: 2023/8/2 16:49
 */
@DisplayName("垂直分片")
@SpringBootTest(classes = ShardingSphereApp.class)
class VerticalCourseTest {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @Test
    @DisplayName("垂直分片::插入")
    void insertOrderUser() {
        UserEntity user = UserEntity.builder().uname("陆小凤").build();
        int insert = userMapper.insert(user);
        Assertions.assertTrue(insert >= 0);

        OrderEntity order = OrderEntity.builder().orderNo(String.valueOf(System.currentTimeMillis()))
                .userId(user.getId()).amount(BigDecimal.valueOf(128.32D)).build();
        int orderInsert = orderMapper.insert(order);
        Assertions.assertTrue(orderInsert >= 0);
    }

    @Test
    @DisplayName("垂直分片::查询")
    void selectOrderUser() {
        List<UserEntity> userEntityList = userMapper.selectList(null);
        List<OrderEntity> orderEntityList = orderMapper.selectList(null);
        Assertions.assertFalse(CollectionUtils.isEmpty(userEntityList));
        Assertions.assertFalse(CollectionUtils.isEmpty(orderEntityList));
    }


}
