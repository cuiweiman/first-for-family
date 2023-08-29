# 读写分离::MySQL 主从配置

> application-rwseparation.yml

1. 安装 docker 环境。[阿里云镜像 docker-ce 安装](https://developer.aliyun.com/mirror/docker-ce)

```bash
# 查看Linux系统是 3.10 版本以上
uname -r
# 安装 docker 依赖包
yum install -y yum-utils \
    device-mapper-persistent-data \
    lvm2
# 配置 docker 安装包的阿里镜像地址
yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

# 更新 docker 源
sed -i 's+download.docker.com+mirrors.aliyun.com/docker-ce+' /etc/yum.repos.d/docker-ce.repo

# 更新、安装并启动 docker 服务
yum makecache fast
yum -y install docker-ce
service docker start
# 安装校验
docker version

# 开机自启的开启和关闭
systemctl enable docker
systemctl disable docker

# 开启或关闭开机自启后，需要重新加载配置
systemctl daemon-reload
```

2. 创建 MySQL 主节点服务

```bash
# 启动 mysql 主节点容器
docker run -d --name=leader-3306 -p 3306:3306 \
-v /data/my-docker/mysql/leader-3306/data:/var/lib/mysql \
-v /data/my-docker/mysql/leader-3306/conf:/etc/mysql/conf.d \
-e MYSQL_ROOT_PASSWORD=Baidu@123 \
mysql:8.0.29

# 调整主节点的配置文件
vim /data/my-docker/mysql/leader-3306/conf/my.cnf

[mysqld]
# 服务 ID，默认 1
server-id=1

# 日志格式 默认 ROW
binlog_format=STATEMENT

# 二进制日志文件名 默认 binlog
log-bin=binlog

# 设置需要复制的数据库,默认复制全部数据库
# binlog-do-db=my_db

# 设置不复制的数据库
# binlog-ignore-db=mysql
# binlog-ignore-db=information_schema

# 重启容器使配置生效, 并登录数据库服务
docker restart leader-3306
docker exec -it leader-3306 /bin/bash
mysql -uroot -pBaidu@123
```

3. 准备 follower 用户，用于从服务器拉去主节点的数据日志

```bash
# mysql -u root -p 进入 mysql 主服务
create user 'mysql_follower'@'%';
alter user 'mysql_follower'@'%' identified with mysql_native_password by 'Baidu@123';
# 给用户赋值 所有库.所有表 的 分片复制权限
grant replication slave on *.* to 'mysql_follower'@'%';
flush privileges;
# 查看主服务器 mysql 状态，并记录 File 和 Position 的值，然后不再操作主服务器 MySQL，避免状态值变化。
mysql> show master status;
+---------------+----------+--------------+------------------+-------------------+
| File          | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+---------------+----------+--------------+------------------+-------------------+
| binlog.000003 |     1070 |              |                  |                   |
+---------------+----------+--------------+------------------+-------------------+
1 row in set (0.01 sec)

```

4. 准备 MySQL 从节点服务器

```bash
# 创建并启动两个容器
docker run -d --name=follower-3307 -p 3307:3306 \
-v /data/my-docker/mysql/follower-3307/data:/var/lib/mysql \
-v /data/my-docker/mysql/follower-3307/conf:/etc/mysql/conf.d \
-e MYSQL_ROOT_PASSWORD=Baidu@123 \
mysql:8.0.29

docker run -d --name=follower-3308 -p 3308:3306 \
-v /data/my-docker/mysql/follower-3308/data:/var/lib/mysql \
-v /data/my-docker/mysql/follower-3308/conf:/etc/mysql/conf.d \
-e MYSQL_ROOT_PASSWORD=Baidu@123 \
mysql:8.0.29

# 分别创建配置文件, 内容大致如下
vim /data/my-docker/mysql/follower-3307/conf/my.cnf

[mysqld]
server-id=2

# 中继日志名,默认 xxx-relay-bin
# relay-log=relay-bin

# 分别重启两个容器
docker restart follower-3307
docker restart follower-3308

```

5. 从节点配置主从关系

```bash
# 在 从节点上 分别 配置主服务器的同步信息
# mysql -u root -h1.117.45.242 -P3307 -p
# mysql -u root -h1.117.45.242 -P3308 -p
mysql> change master to master_host='1.117.45.242',
    -> master_user='mysql_follower', master_password='Baidu@123',master_port=3306,
    -> master_log_file='binlog.000003', master_log_pos=1070;

# 启动主从同步
start slave;
show slave status\G
```

# 垂直分片:: MySQL 配置
























