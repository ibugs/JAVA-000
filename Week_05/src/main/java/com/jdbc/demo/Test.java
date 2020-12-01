package src.main.java.com.jdbc.demo;

import java.sql.*;

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
        // t.update();
        t.insertBatch();
    }

    /**
     * 更新操作
     */
    public void update() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = com.jdbc.demo.JdbcUtils.getConnection();
            // 设置关闭自动提交
            connection.setAutoCommit(false);
            String sql = "update work_date set work_date_flag = ?, trade_date_flag = ? where nature_date = ? ";

            /***
             * 原始数据
             * mysql> select * from work_date where nature_date = 20191222;
             * +-------------+----------------+-----------------+
             * | nature_date | work_date_flag | trade_date_flag |
             * +-------------+----------------+-----------------+
             * |    20191222 | 0              | 0               |
             * +-------------+----------------+-----------------+
             * 1 row in set (0.02 sec)
             */
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 2);
            preparedStatement.setInt(3, 20191222);
            int resultnum = preparedStatement.executeUpdate();

            /*** 一个可以引起异常的例子，出现异常之后回滚 ***/
            // sql = "updated work_date set wok_date = ?";
            // preparedStatement = connection.prepareStatement(sql);
            // preparedStatement.setInt(1, 111);

            // resultnum = preparedStatement.executeUpdate();

            System.out.println("更新影响的条数为=>" + resultnum);
            connection.commit();
        } catch (SQLException e) {
            // 出现异常就回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
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


    /***
     * 批量插入，使用Statement. addBatch
     */
    public void insertBatch() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO work_date(nature_date, work_date_flag, trade_date_flag)" +
                    "VALUES(20201117,1,1)";

            statement.addBatch(sql);

            sql = "INSERT INTO work_date(nature_date, work_date_flag, trade_date_flag)" +
                    "VALUES(20201118,2,2)";

            statement.addBatch(sql);

            sql = "UPDATE work_date set work_date_flag = 9, trade_date_flag = 9 where nature_date = 20201116";
            statement.addBatch(sql);

            int[] resultCount = statement.executeBatch();
            connection.commit();

            System.out.println(resultCount[0] + "->" + resultCount[1] + "->" + resultCount[2]);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            JdbcUtils.releaseDB(connection, null, null);
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
            connection = com.jdbc.demo.JdbcUtils.getConnection();
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
            com.jdbc.demo.JdbcUtils.releaseDB(connection, preparedStatement, resultSet);
        }

    }
}
