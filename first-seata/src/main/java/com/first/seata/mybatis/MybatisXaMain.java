package com.first.seata.mybatis;

import com.first.seata.mybatis.config.MybatisConfig;
import com.first.seata.mybatis.mapper.CourseMapper;
import io.seata.core.exception.TransactionException;
import io.seata.rm.RMClient;
import io.seata.tm.TMClient;
import io.seata.tm.api.GlobalTransaction;
import io.seata.tm.api.GlobalTransactionContext;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @description: 测试
 * @author: cuiweiman
 * @date: 2023/8/21 17:16
 */
public class MybatisXaMain {

    public static void main(String[] args) throws Exception {
        // noXa();
        withXa();
    }

    private static void withXa() throws TransactionException {
        String appId = "demo";
        String groupName = "my_test_tx_group";
        // 初始化 TM 事务管理器 和 RM 资源管理器
        TMClient.init(appId, groupName);
        RMClient.init(appId, groupName);

        SqlSessionFactory aaaSqlSessionFactory = MybatisConfig.aaaSqlSessionFactory(true);
        SqlSessionFactory bbbSqlSessionFactory = MybatisConfig.bbbSqlSessionFactory(true);
        // 创建并开启分布式事务
        GlobalTransaction globalTransaction = GlobalTransactionContext.getCurrentOrCreate();
        globalTransaction.begin();

        try (SqlSession aaaSqlSession = aaaSqlSessionFactory.openSession();
             SqlSession bbbSqlSession = bbbSqlSessionFactory.openSession()) {
            CourseMapper aaaCourseMapper = aaaSqlSession.getMapper(CourseMapper.class);
            CourseMapper bbbCourseMapper = bbbSqlSession.getMapper(CourseMapper.class);

            aaaCourseMapper.aaaReduce();
            bbbCourseMapper.bbbAdd();

            // int a = 1 / 0;

            // 提交
            globalTransaction.commit();

        } catch (Exception e) {
            System.out.println("\terror: " + e);
            // 回滚
            globalTransaction.rollback();
        }
    }

    private static void noXa() {
        SqlSessionFactory aaaSqlSessionFactory = MybatisConfig.aaaSqlSessionFactory(false);
        SqlSessionFactory bbbSqlSessionFactory = MybatisConfig.bbbSqlSessionFactory(false);

        try (SqlSession aaaSqlSession = aaaSqlSessionFactory.openSession(true);
             SqlSession bbbSqlSession = bbbSqlSessionFactory.openSession(true)) {

            CourseMapper aaaCourseMapper = aaaSqlSession.getMapper(CourseMapper.class);
            CourseMapper bbbCourseMapper = bbbSqlSession.getMapper(CourseMapper.class);

            aaaCourseMapper.aaaReduce();
            bbbCourseMapper.bbbAdd();

        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }
}
