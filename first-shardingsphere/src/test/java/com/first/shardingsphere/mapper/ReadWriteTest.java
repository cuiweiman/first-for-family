package com.first.shardingsphere.mapper;

import com.first.shardingsphere.ShardingSphereApp;
import com.first.shardingsphere.base.UserEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

/**
 * application-rwseparation.yml
 *
 * @description: 读写分离测试
 * @author: cuiweiman
 * @date: 2023/8/2 15:12
 */
@DisplayName("读写分离测试")
@SpringBootTest(classes = ShardingSphereApp.class)
class ReadWriteTest {

    @Resource
    private UserMapper userMapper;

    @Test
    @DisplayName("数据写入")
    void testInsert() {
        UserEntity user = UserEntity.builder().uname("张三丰").build();
        int insert = userMapper.insert(user);
        Assertions.assertTrue(insert >= 0);
    }

    @Test
    @DisplayName("负载均衡::数据查询")
    void testSelect() {
        List<UserEntity> userEntities = userMapper.selectList(null);
        // 不为空
        Assertions.assertFalse(CollectionUtils.isEmpty(userEntities));
        System.out.println(userEntities);
        // 查询两次，观察负载均衡情况
        List<UserEntity> userEntities2 = userMapper.selectList(null);
        System.out.println(userEntities2);

        userMapper.selectList(null);
        userMapper.selectList(null);
    }

    @Test
    @Transactional
    @DisplayName("事务::均使用 master 数据源")
    void testTransaction() {
        // 测试过程中，开启事务后，会自动回滚
        UserEntity user = UserEntity.builder().uname(UUID.randomUUID().toString().replace("-", "")).build();
        userMapper.insert(user);
        List<UserEntity> userEntities = userMapper.selectList(null);
        Assertions.assertFalse(CollectionUtils.isEmpty(userEntities));
        System.out.println(userEntities);
        // 查询两次，观察负载均衡情况，可以发现，使用的都是 master 数据源
        List<UserEntity> userEntities2 = userMapper.selectList(null);
        System.out.println(userEntities2);
    }


}
