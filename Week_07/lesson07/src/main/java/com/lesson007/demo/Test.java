package com.lesson007.demo;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

public class Test {
    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static {
        try {
            // 1. 加载配置文件
            InputStream in = Test.class.getClassLoader().
                    getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(in);

            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            // 2. 加载驱动
            Class.forName(driver);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.getException();
        }
    }

    /***
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 释放连接
     * @param connection
     * @param preparedStatement
     * @param resultSet
     */
    public static void releaseDB(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.insertBatch(100);
        //t.insert();
        //t.insertBatch2();
    }


    /***
     * 批量插入，使用Statement. addBatch
     */
    public void insertBatch(int batchCount) {
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
            for (int i = 0; i < batchCount; i++) {
                preparedStatement.setInt(1, 2 + i);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(4, "张三" + i);
                preparedStatement.setInt(5, (int) (Math.random() * 100));
                preparedStatement.setLong(6, new Random().nextLong());
                preparedStatement.setString(7, "北京市" + i);
                preparedStatement.setInt(8, 1);
                preparedStatement.setString(9, "zhangsan" + i);
                preparedStatement.setString(10, new Random().nextLong() + "");
                preparedStatement.addBatch();
                System.out.println("index ->" + i);
            }
            // statement.addBatch(sql);

            int[] resultCount = preparedStatement.executeBatch();
            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println("耗时->" + (end - start));
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            Test.releaseDB(connection, null, null);
        }
    }

    /***
     * 使用Statement
     */
    public void insertBatch2() {
        Connection connection = null;
        try {
            long start = System.currentTimeMillis();
            connection = Test.getConnection();
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            for (int i = 0; i < 1000000; i++) {
                Timestamp createTime = Timestamp.valueOf(LocalDateTime.now());
                Timestamp updateTime = Timestamp.valueOf(LocalDateTime.now());
                String name = "张三" + i;
                Integer age = (int) (Math.random() * 100);
                Long phone = new Random().nextLong();
                String address = "北京市" + i;
                Integer status = 1;
                String nickName = "zhangsan" + i;
                String certificateNo = new Random().nextLong() + "";
                String sql = "INSERT INTO tb_user (id, create_time, update_time," +
                        " name, age, phone, address, status, nickname," +
                        " certificate_no) values (" + i + ",'" + createTime + "','" + updateTime + "','" + name
                        + "'," + age + "," + phone + ",'" + address + "'," + status + ",'" + nickName + "'," + certificateNo + ");";
                statement.addBatch(sql);
                //System.out.println("sql=>"+sql);
                System.out.println("index ->" + i);
            }
            statement.executeBatch();
            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println("耗时->" + (end - start));
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            Test.releaseDB(connection, null, null);
        }
    }

    /**
     * 插入操作
     */
    public void insert() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Long start = System.currentTimeMillis();
            for (int i = 0; i < 1000000; i++) {
                connection = Test.getConnection();
                String sql = "INSERT INTO tb_user (id, create_time, update_time," +
                        " name, age, phone, address, status, nickname," +
                        " certificate_no) values (?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql);

                //Faker faker = new Faker();

                preparedStatement.setInt(1, 2 + i);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(4, "zhangsan" + i);
                preparedStatement.setInt(5, (int) (Math.random() * 100));
                preparedStatement.setLong(6, new Random().nextLong());
                preparedStatement.setString(7, "MNV" + i);
                preparedStatement.setInt(8, 1);
                preparedStatement.setString(9, "zs");
                preparedStatement.setString(10, new Random().nextLong() + "");

                // 返回影响的行数
                int num = preparedStatement.executeUpdate();
                Test.releaseDB(connection, preparedStatement, null);
            }
            long end = System.currentTimeMillis();
            System.out.println("耗时" + (end - start));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Test.releaseDB(connection, preparedStatement, null);
        }
    }
}
