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
 * application-mod.yml
 *
 * @description: Standard 策略::取模分片算法 MOD 测试
 * @author: cuiweiman
 * @date: 2023/8/3 16:48
 */
@DisplayName("标准策略::MOD分片算法")
@SpringBootTest(classes = ShardingSphereApp.class)
class ModCourseMapperTest {

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
        CourseEntity courseEntity = courseMapper.selectById(1686947115386802177L);
        System.out.println(courseEntity);
        Assertions.assertNotNull(courseEntity.getCid());
    }

    @Test
    @DisplayName("分页查询")
    void listGeByPage() {
        LambdaQueryWrapper<CourseEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.ge(CourseEntity::getCid, 1686947115386802177L);

        IPage<CourseEntity> page = new Page<>(1, 10);
        IPage<CourseEntity> courseEntityPage = courseMapper.selectPage(page, queryWrapper);
        System.out.println(courseEntityPage.getRecords());
        Assertions.assertEquals(courseEntityPage.getRecords().size(), 10);
    }

    @Test
    @DisplayName("范围查询")
    void listByRange() {
        LambdaQueryWrapper<CourseEntity> queryWrapper = Wrappers.lambdaQuery();
        // between ... and ... 可以查询成功，特别特别慢，用了 1h 多。
        // queryWrapper.between(CourseEntity::getCid, 1686947115386802177L, 1686947118029213698L);
        // >= and <= 也特别慢
        // queryWrapper.ge(CourseEntity::getCid, 1686947115386802177L).le(CourseEntity::getCid, 1686947118029213698L);

        // >= 可以顺利查询
        queryWrapper.ge(CourseEntity::getCid, 1686947115386802177L);
        // <= 报错: range unbounded on this side, 可以判断包含了 小于 的筛选都会有问题
        // queryWrapper.le(CourseEntity::getCid, 1686947118029213698L);

        IPage<CourseEntity> page = new Page<>(1, 10);
        IPage<CourseEntity> courseEntityPage = courseMapper.selectPage(page, queryWrapper);
        System.out.println(courseEntityPage.getRecords());
        Assertions.assertFalse(CollectionUtils.isEmpty(courseEntityPage.getRecords()));
    }


}