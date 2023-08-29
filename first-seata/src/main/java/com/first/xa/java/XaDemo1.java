package com.first.xa.java;

import com.mysql.cj.jdbc.MysqlXADataSource;
import com.mysql.cj.jdbc.MysqlXid;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/8/16 11:34
 */
public class XaDemo1 {
    public final static String LOCAL_MYSQL_PASSWORD = "*";

    public static void main(String[] args) throws Exception {
        String courseDb0 = "jdbc:mysql://127.0.0.1:3306/coursedb_0?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        String courseDb1 = "jdbc:mysql://127.0.0.1:3306/coursedb_1?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        String user = "root";

        // 创建 XA 数据源
        MysqlXADataSource aaaDataSource = new MysqlXADataSource();
        aaaDataSource.setUrl(courseDb0);
        aaaDataSource.setUser(user);
        aaaDataSource.setPassword(LOCAL_MYSQL_PASSWORD);

        MysqlXADataSource bbbDataSource = new MysqlXADataSource();
        bbbDataSource.setUrl(courseDb1);
        bbbDataSource.setUser(user);
        bbbDataSource.setPassword(LOCAL_MYSQL_PASSWORD);

        // 获取 XA协议 的 RM 资源管理器
        XAConnection aaaXaConnection = aaaDataSource.getXAConnection();
        XAResource aaaXaResource = aaaXaConnection.getXAResource();

        XAConnection bbbXaConnection = bbbDataSource.getXAConnection();
        XAResource bbbXaResource = bbbXaConnection.getXAResource();

        // 创建 事务 ID XID
        Xid aaaXid = new MysqlXid("coursedb_0".getBytes(), "course_0".getBytes(), 1);
        Xid bbbXid = new MysqlXid("coursedb_1".getBytes(), "course_1".getBytes(), 1);

        // DB0 封装 XA 事务
        String aaaSql = "update course_0 set c_status=c_status-100 where cid = 1686947115428745218";
        aaaXaResource.start(aaaXid, XAResource.TMNOFLAGS);
        aaaXaConnection.getConnection().prepareStatement(aaaSql).execute();
        aaaXaResource.end(aaaXid, XAResource.TMSUCCESS);


        // DB1 封装 XA 事务
        String bbbSql = "update course_1 set c_status=c_status+100 where cid = 1686947115286138881";
        bbbXaResource.start(bbbXid, XAResource.TMNOFLAGS);
        bbbXaConnection.getConnection().prepareStatement(bbbSql).execute();
        bbbXaResource.end(bbbXid, XAResource.TMSUCCESS);

        try {
            // XA prepare 阶段
            int aPrepare = aaaXaResource.prepare(aaaXid);
            int bPrepare = bbbXaResource.prepare(bbbXid);

            int a = 1 / 0;

            // XA 提交
            if (XAResource.XA_OK == aPrepare && XAResource.XA_OK == bPrepare) {
                aaaXaResource.commit(aaaXid, false);
                bbbXaResource.commit(bbbXid, false);
            } else {
                aaaXaResource.rollback(aaaXid);
                bbbXaResource.rollback(bbbXid);
            }
        } catch (Exception e) {
            aaaXaResource.rollback(aaaXid);
            bbbXaResource.rollback(bbbXid);
        }
    }

}
