package com.first.shardingsphere.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @description: mybatis-plus
 * @author: cuiweiman
 * @date: 2023/8/1 14:50
 */
@Configuration
@MapperScan("com.first.shardingsphere.mapper")
public class MybatisPlusConfig {

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, GlobalConfig globalConfig) throws Exception {
        MybatisConfiguration configuration = new MybatisConfiguration();
        // 关闭一级缓存: 缓存范围只对当前语句有效，当前查询结束后缓存清空，可以认为关闭了一级缓存
        configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        // 关闭二级缓存
        configuration.setCacheEnabled(false);
        // 空值对应的类型,一般用于 Oracle 数据库
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        // 开启驼峰转换
        configuration.setMapUnderscoreToCamelCase(true);
        // 返回 Map 结果集的字段，下划线转驼峰
        configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
        // 乐观锁插件、分页插件
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        configuration.addInterceptor(mybatisPlusInterceptor);

        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        // dataSource
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setGlobalConfig(globalConfig);
        sqlSessionFactory.setConfiguration(configuration);
        // xml 文件路径
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mappers/**/*.xml"));
        return sqlSessionFactory.getObject();
    }

    @Bean("globalConfig")
    public GlobalConfig globalConfig() {
        GlobalConfig conf = new GlobalConfig();
        // close mybatis-plus banner
        conf.setBanner(false);
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setColumnFormat("`%s`");
        conf.setDbConfig(dbConfig);
        return conf;
    }

}
