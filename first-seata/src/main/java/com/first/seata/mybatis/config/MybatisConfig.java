package com.first.seata.mybatis.config;

import com.first.seata.mybatis.mapper.CourseMapper;
import io.seata.rm.datasource.xa.DataSourceProxyXA;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * @description: SqlSessionFactory 配置
 * @author: cuiweiman
 * @date: 2023/8/16 15:11
 */
public class MybatisConfig {
    public static final String LOCAL_MYSQL_PASSWORD = "Baidu@123";

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USERNAME = "root";

    public static SqlSessionFactory aaaSqlSessionFactory(boolean isXa) {
        String url = "jdbc:mysql://127.0.0.1:3306/coursedb_0?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        return createSqlSessionFactory(url, isXa);
    }

    public static SqlSessionFactory bbbSqlSessionFactory(boolean isXa) {
        String url = "jdbc:mysql://127.0.0.1:3306/coursedb_1?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        return createSqlSessionFactory(url, isXa);
    }

    public static SqlSessionFactory createSqlSessionFactory(String url, boolean isXa) {
        DataSource dataSource = new PooledDataSource(DRIVER, url, USERNAME, LOCAL_MYSQL_PASSWORD);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment;
        if (isXa) {
            // XA 代理数据源
            DataSourceProxyXA dataSourceProxyXa = new DataSourceProxyXA(dataSource);
            environment = new Environment("development", transactionFactory, dataSourceProxyXa);
        } else {
            environment = new Environment("development", transactionFactory, dataSource);
        }
        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration(environment);
        configuration.addMapper(CourseMapper.class);
        return new SqlSessionFactoryBuilder().build(configuration);
    }


}
