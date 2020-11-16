package com.spring.demo.service;

import com.spring.demo.entity.WorkDate;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @author wanghao
 * 2020-11-16 19:22
 */
@Service
public class WorkDateImpl implements WorkDateService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int create(Integer nature_date, Integer work_date_flag, Integer trade_date_flag) {
        return jdbcTemplate.update("insert into work_date" +
                        " (nature_date, work_date_flag, trade_date_flag) values (?, ?, ?)",
                nature_date, work_date_flag, trade_date_flag);
    }

    @Override
    public int delete(Integer nature_date) {
        return jdbcTemplate.update("delete from work_date where nature_date =?", nature_date);
    }

    /**
     mysql> select * from work_date where nature_date = 20201118;
     +-------------+----------------+-----------------+
     | nature_date | work_date_flag | trade_date_flag |
     +-------------+----------------+-----------------+
     |    20201118 | 5              | 6               |
     +-------------+----------------+-----------------+
     1 row in set (0.00 sec)
     **/
    @Override
    public int update(Integer nature, Integer workDateFlag, Integer tradeDateFlag) {
        return jdbcTemplate.update("update work_date set work_date_flag = ?, trade_date_flag = ? WHERE nature_date = ?",
                workDateFlag, tradeDateFlag, nature);
    }

    @Override
    public String query(Integer natureDate) {
        int resultCount = jdbcTemplate.queryForObject("select count(1) from work_date", Integer.class);
        return "全部的数量为" + resultCount;
    }
}
