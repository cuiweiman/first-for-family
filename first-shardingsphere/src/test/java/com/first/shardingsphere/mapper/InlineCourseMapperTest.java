package com.first.shardingsphere.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.first.shardingsphere.ShardingSphereApp;
import com.first.shardingsphere.base.CourseEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

/**
 * application-inline.yml
 *
 * @description: Standard 策略::行表达式 Inline 分片算法
 * @author: cuiweiman
 * @date: 2023/8/1 16:48
 */
@DisplayName("标准策略::inline分片算法")
@SpringBootTest(classes = ShardingSphereApp.class)
class InlineCourseMapperTest {

    @Resource
    private CourseMapper courseMapper;

    @Test
    @DisplayName("根据分片键存储")
    void save() {
        for (int i = 0; i < 100; i++) {
            CourseEntity course = CourseEntity.builder().cName("Java-" + i).userId(1001L).cStatus("" + i).build();
            int insert = courseMapper.insert(course);
            Assertions.assertEquals(insert, 1);
        }
    }

    @Test
    @DisplayName("精确查找")
    void getOne() {
        CourseEntity courseEntity = courseMapper.selectById(1685988237769711617L);
        System.out.println(courseEntity);
        Assertions.assertNotNull(courseEntity.getCid());
    }

    @Test
    @DisplayName("分页查询,查询条件:GE-inline 默认不支持")
    void listGeByPage() {
        LambdaQueryWrapper<CourseEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.ge(CourseEntity::getCid, 1685988237769711617L);

        IPage<CourseEntity> page = new Page<>(1, 10);
        IPage<CourseEntity> courseEntityPage = courseMapper.selectPage(page, queryWrapper);
        System.out.println(courseEntityPage.getRecords());
        Assertions.assertEquals(courseEntityPage.getRecords().size(), 10);
    }

    @Test
    @DisplayName("范围查询-inline 默认不支持")
    void listByRange() {
        LambdaQueryWrapper<CourseEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.between(CourseEntity::getCid, 1685988237736157186L, 1685988237769711617L);

        IPage<CourseEntity> page = new Page<>(1, 10);
        IPage<CourseEntity> courseEntityPage = courseMapper.selectPage(page, queryWrapper);
        System.out.println(courseEntityPage.getRecords());
        Assertions.assertFalse(CollectionUtils.isEmpty(courseEntityPage.getRecords()));
    }


}