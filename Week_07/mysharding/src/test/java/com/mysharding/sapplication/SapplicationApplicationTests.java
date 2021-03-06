package com.mysharding.sapplication;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class SapplicationApplicationTests {

    public static void main(String[] args) throws SQLException {
        // https://shardingsphere.apache.org/document/legacy/4.x/document/cn/manual/sharding-jdbc/usage/read-write-splitting/#%E4%BD%BF%E7%94%A8%E5%8E%9F%E7%94%9Fjdbc

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置主库
        BasicDataSource masterDataSource = new BasicDataSource();
        masterDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        masterDataSource.setUrl("jdbc:mysql://127.0.0.1:33309/test1202?characterEncoding=utf-8");
        masterDataSource.setUsername("root");
        masterDataSource.setPassword("root");
        dataSourceMap.put("ds_master", masterDataSource);

        // 配置第一个从库
        BasicDataSource slaveDataSource1 = new BasicDataSource();
        slaveDataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        slaveDataSource1.setUrl("jdbc:mysql://127.0.0.1:33306/test1202?characterEncoding=utf-8");
        slaveDataSource1.setUsername("root");
        slaveDataSource1.setPassword("root");
        dataSourceMap.put("ds_slave0", slaveDataSource1);

        // 配置第二个从库 TODO
        Properties sqlShow = new Properties();
        sqlShow.setProperty("props.sql.show", "true");

        // 配置读写分离规则
        MasterSlaveRuleConfiguration masterSlaveRuleConfig =
                new MasterSlaveRuleConfiguration("ds_master_slave",
                        "ds_master",
                        Arrays.asList("ds_slave0"));
        // 获取数据源对象
        DataSource dataSource = MasterSlaveDataSourceFactory.
                createDataSource(dataSourceMap,
                        masterSlaveRuleConfig, sqlShow);


        String selectSql = "SELECT t.* FROM tb_user AS t WHERE id = ?";
        Connection conn = dataSource.getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, 100);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    System.out.println(rs.getInt(1));
                    System.out.println(rs.getString(4));
                }
            }
        }

        String updateSql = "UPDATE tb_user SET tb_user.name =? WHERE id = ?";
        try(PreparedStatement preparedStatement  = conn.prepareStatement(updateSql)){
           preparedStatement.setString(1, "wanghao");
           preparedStatement.setInt(2, 100);
           int result = preparedStatement.executeUpdate();
            System.out.println(result);
        }

    }
}
