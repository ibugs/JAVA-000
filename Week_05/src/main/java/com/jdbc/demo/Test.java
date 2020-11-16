package com.jdbc.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wanghao
 * 2020-11-16 18:03
 */
public class Test {
    public static void main(String[] args) {
        Test t = new Test();
        // t.query();
        // t.insert();
        // t.query();
        // t.delete();
        // t.query();
        t.update();
    }

    /**
     * 更新操作
     */
    public void update() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sql = "update work_date set work_date_flag = ?, trade_date_flag = ? where nature_date = ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 20191222);

            int resultnum = preparedStatement.executeUpdate();
            System.out.println("更新影响的条数为=>" + resultnum);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.releaseDB(connection, preparedStatement, null);
        }
    }


    /**
     * 删除操作
     */
    public void delete() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String sql = "DELETE from work_date where nature_date < ?";
            connection = JdbcUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 20190523);
            int num = preparedStatement.executeUpdate();

            System.out.println("删除影响到了多少行？" + num);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseDB(connection, preparedStatement, null);
        }

    }

    /**
     * 插入操作
     */
    public void insert() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sql = "INSERT INTO work_date(nature_date, work_date_flag, trade_date_flag) values (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, 20201116);
            preparedStatement.setInt(2, 1);
            preparedStatement.setInt(3, 1);

            // 返回影响的行数
            int num = preparedStatement.executeUpdate();
            System.out.println("一共影响了" + num + "行");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseDB(connection, preparedStatement, null);
        }

    }

    /***
     * 查询操作
     */
    public void query() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sql = "select count(*) from work_date";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer result = resultSet.getInt(1);
                System.out.println("数据库的总结果为 => " + result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseDB(connection, preparedStatement, resultSet);
        }

    }
}
