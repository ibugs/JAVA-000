package com.lesson007.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author wanghao
 * 2020-12-02 13:52
 */
public class RouttingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        /***
         * 返回当前线程正在使用的代表数据库的枚举对象
         */
        return DynamicSwitchDBTypeUtil.get();
    }
}
