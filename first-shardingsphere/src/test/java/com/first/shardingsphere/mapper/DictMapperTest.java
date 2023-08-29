package com.first.shardingsphere.mapper;

import com.first.shardingsphere.ShardingSphereApp;
import com.first.shardingsphere.base.DictEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * application-broadcast.yml
 *
 * @description: 关联表测试
 * @author: cuiweiman
 * @date: 2023/8/3 16:48
 */
@DisplayName("广播表")
@SpringBootTest(classes = ShardingSphereApp.class)
class DictMapperTest {

    @Resource
    private DictMapper dictMapper;


    @Test
    @DisplayName("入库")
    void insertDictTest() {
        DictEntity build = DictEntity.builder().dictType("type1").build();
        dictMapper.insert(build);
    }

    @Test
    @DisplayName("查询")
    void selectDict() {
        List<DictEntity> list = dictMapper.selectList(null);
        list.forEach(System.out::println);
        // 根据日志, 观测查询的库表
        for (int i = 0; i < 10; i++) {
            dictMapper.selectList(null);
        }
    }


}
