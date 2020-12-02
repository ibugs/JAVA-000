package com.lesson007.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghao
 * 2020-12-02 13:53
 */
@Configuration
public class DataSourceConfig {

    /***
     * 将创建的master数据源存入Spring容器中，并且注入内容
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /***
     * 将创建的slave数据源存入Spring容器中，并且注入内容
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    /***
     * 决定最终要使用的数据源
     * @param masterDataSource
     * @param slaveDataSource
     * @return
     */
    @Bean
    public DataSource targetDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                       @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        // 存放主数据源和从数据源
        Map<Object, Object> targetDataSource = new HashMap<>();

        // 往map中添加主数据源
        targetDataSource.put(DBTypeEnum.MASTER, masterDataSource);
        // 往map中添加从数据源
        targetDataSource.put(DBTypeEnum.SLAVE, slaveDataSource);

        // 创建 routtingDataSource 用来实现动态切换
        RouttingDataSource routtingDataSource = new RouttingDataSource();
        // 绑定所有的数据源
        routtingDataSource.setTargetDataSources(targetDataSource);
        // 设置默认的数据源
        routtingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routtingDataSource;
    }
}

