学习笔记

##### 第13节课作业实践

###### 一、按照自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。

[代码](./lesson07/src/main/java/com/lesson007/demo/Test.java)

1. 使用JDBC preparedStatement.executeUpdate


如果不关闭连接，不关闭Connection的时候，就会出现下面的错误
```
java.sql.SQLNonTransientConnectionException:
	 Data source rejected establishment of connection,  message from server: "Too many connections"
```

connection 用一次，然后释放一次。总共使用了100万次，再释放100万。
如果不释放，那么就会出现上述错误，Too many connections

耗时：6个小时
耗时点
1) Faker
2) 每一次都重新获取connection

```
    public void insert() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Long start = System.currentTimeMillis();
            for(int i = 0; i < 1000000; i++){
                connection = Test.getConnection();
                String sql = "INSERT INTO tb_user (id, create_time, update_time," +
                        " name, age, phone, address, status, nickname," +
                        " certificate_no) values (?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql);

                Faker faker = new Faker();

                preparedStatement.setInt(1,2+i);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(4, faker.name());
                preparedStatement.setInt(5, (int) (Math.random() * 100));
                preparedStatement.setLong(6, new Random().nextLong());
                preparedStatement.setString(7, faker.stateAbbr());
                preparedStatement.setInt(8, 1);
                preparedStatement.setString(9, faker.lastName());
                preparedStatement.setString(10, new Random().nextLong()+"");

                // 返回影响的行数
                int num = preparedStatement.executeUpdate();
                Test.releaseDB(connection, preparedStatement, null);
            }
            long end = System.currentTimeMillis();
            System.out.println("耗时"+(end - start));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Test.releaseDB(connection, preparedStatement, null);
        }
    }
```

2. 使用JDBC PreparedStatement executeBatch();

耗时：204秒

改进点   

1）整个过程 connection 只使用一次，大大减少了创建connection和销毁的过程

2) 使用addBatch，将100万的数据编译一次，传递给MySQL执行。

``` 
    public void insertBatch() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            long start = System.currentTimeMillis();
            connection = Test.getConnection();
            //Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO tb_user (id, create_time, update_time," +
                    " name, age, phone, address, status, nickname," +
                    " certificate_no) values (?,?,?,?,?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(sql);
            for(int i = 0; i< 1000000; i++){
                preparedStatement.setInt(1,2+i);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(4, "张三"+i);
                preparedStatement.setInt(5, (int) (Math.random() * 100));
                preparedStatement.setLong(6, new Random().nextLong());
                preparedStatement.setString(7, "北京市"+i);
                preparedStatement.setInt(8, 1);
                preparedStatement.setString(9, "zhangsan"+i);
                preparedStatement.setString(10, new Random().nextLong()+"");
                preparedStatement.addBatch();
                System.out.println("index ->"+i);
            }
            // statement.addBatch(sql);

            int[] resultCount = preparedStatement.executeBatch();
            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println("耗时->"+(end - start));
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            Test.releaseDB(connection, null, null);
        }
    }
```

3) 使用 Statement addBatch

耗时：214秒

虽然Statement 和 PreparedStatement 耗时没有差多少，
但是在里面拼接SQL就非常之难受。

```
    public void insertBatch2() {
        Connection connection = null;
        try {
            long start = System.currentTimeMillis();
            connection = Test.getConnection();
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            for(int i = 0; i< 1000000; i++){
                Timestamp createTime = Timestamp.valueOf(LocalDateTime.now());
                Timestamp updateTime = Timestamp.valueOf(LocalDateTime.now());
                String name = "张三"+i;
                Integer age = (int) (Math.random() * 100);
                Long phone = new Random().nextLong();
                String address = "北京市"+i;
                Integer status = 1;
                String nickName = "zhangsan"+i;
                String certificateNo = new Random().nextLong()+"";
                String sql = "INSERT INTO tb_user (id, create_time, update_time," +
                        " name, age, phone, address, status, nickname," +
                        " certificate_no) values ("+i+",'"+createTime+"','"+updateTime+"','"+name
                        +"',"+age+","+phone+",'"+address+"',"+status+",'"+nickName+"',"+certificateNo+");";
                statement.addBatch(sql);
                //System.out.println("sql=>"+sql);
                System.out.println("index ->"+i);
            }
            statement.executeBatch();
            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println("耗时->"+(end - start));
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            Test.releaseDB(connection, null, null);
        }
    }
```

4) 使用MySQl的存储过程

TODO

##### 第14节课作业实践

[Docker方式实现MySQL主从复制](https://www.jianshu.com/p/661f70a7274e)
[Docker安装MySQL](https://www.jianshu.com/p/d9b6bbc7fd77)

准备工作，使用Docker配置MySQL的主从复制

``` 
1）使用Docker运行MySQL容器
docker run -di --name=master_mysql -p 33309:3306 -e MYSQL_ROOT_PASSWORD=root mysql:5.7
使用docker配置主
表示这个容器中使用3306（第二个）映射到本机的端口号为33309（第一个） 
mysql -u root -h 127.0.0.1 -P 33309 -p
通过这个可以访问主库


docker run -di --name=slave_mysql -p 33306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:5.7

2) 进入Docker的主中
docker exec -it master_mysql /bin/bash

apt-get update
apt-get install vim

3) 编辑主的配置文件
cd /etc/mysql
编辑 my.cnf

主
[mysqld]
server-id=10
log-bin=mysql-bin
innodb_flush_log_at_trx_commit=1
sync_binlog=1


docker restart master_mysql

4) 再主上面创建slave用户名，并配置权限

docker exec -it master_mysql /bin/bash
mysql -uroot -proot

CREATE user 'slave'@'%' identified by 'slave';
GRANT REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'slave'@'%';


5) 配置从
docker exec -it slave_mysql /bin/bash
cd /etc/mysql
编辑 my.cnf

从

[mysqld]
server-id=11
log-bin=mysql-slave-bin
relay_log=edu-mysql-relay-bin

docker restart slave_mysql


6) 配置从连接到主的master
docker inspect --format='{{.NetworkSettings.IPAddress}}' master_mysql

docker exec -it slave_mysql /bin/bash
mysql -uroot -proot

change master to master_host='172.17.0.2', master_user='slave', master_password='slave', master_port=3306, master_log_file='mysql-bin.000001', master_log_pos=617, master_connect_retry=30;

```



###### 一、读写分离-动态切换数据源版本1.0

TODO

###### 二、读写分离-数据库框架版本2.0 

TODO
