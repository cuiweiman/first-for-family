package com.first;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @description: MyBatis 多数据源配置
 * @author: cuiweiman
 * @date: 2023/8/31 16:43
 */
@Configuration
@MapperScans({
        @MapperScan(basePackages = {"com.first.mappers.db1"}, sqlSessionFactoryRef = "session1"),
        @MapperScan(basePackages = {"com.first.mappers.db2"}, sqlSessionFactoryRef = "session2")
})
public class DatasourceConfig {

    @Bean(name = "session1")
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("db1") DataSource db1) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(db1);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "session2")
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("db2") DataSource db2) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(db2);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource.course1")
    public DataSource db1() {
        return new DruidDataSource();
    }

    @Bean(name = "db2")
    @ConfigurationProperties(prefix = "spring.datasource.course2")
    public DataSource db2() {
        return new DruidDataSource();
    }

}
