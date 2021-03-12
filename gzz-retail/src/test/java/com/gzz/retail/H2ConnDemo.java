package com.gzz.retail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class H2ConnDemo {
    /**
     * 1、以嵌入式(本地)连接方式连接H2数据库
     * 　　这种连接方式默认情况下只允许有一个客户端连接到H2数据库，有客户端连接到H2数据库之后，
     *     此时数据库文件就会被锁定，那么其他客户端就无法再连接了。
     *
     * 　　连接语法：jdbc:h2:[file:][<path>]<databaseName>
     *
     * 　　例如：
     * 　　　　jdbc:h2:~/test //连接位于用户目录下的test数据库
     * 　　　　jdbc:h2:file:/data/sample
     * 　　　　jdbc:h2:file:E:/H2/gacl(Windows only)
     *
     * 2、使用TCP/IP的服务器模式(远程连接)方式连接H2数据库(推荐)
     * 　　这种连接方式就和其他数据库类似了，是基于Service的形式进行连接的，因此允许多个客户端同
     *     时连接到H2数据库
     *
     * 　　连接语法：jdbc:h2:tcp://<server>[:<port>]/[<path>]<databaseName>
     * 　　范例：jdbc:h2:tcp://localhost/~/test
     *
     * 3、H2数据库的内存模式
     *
     *    (1)、H2数据库被称为内存数据库，因为它支持在内存中创建数据库和表
     *
     *    (2)、注意：如果使用H2数据库的内存模式，那么我们创建的数据库和表都只是保存在内存中，
     *               一旦服务器重启，那么内存中的数据库和表就不存在了。
     *
     * 以嵌入式(本地)连接方式连接H2数据库
     */
    //private static final String JDBC_URL = "jdbc:h2:C:/H2/abc";

    /**
     * 使用TCP/IP的服务器模式(远程连接)方式连接H2数据库(推荐)
     */
    //private static final String JDBC_URL = "jdbc:h2:tcp://10.35.14.122/C:/H2/user";
    private static final String JDBC_URL = "jdbc:h2:file:D:/opt/H2testDB";

    private static final String USER = "user";
    private static final String PASSWORD = "1234";
    private static final String DRIVER_CLASS = "org.h2.Driver";

    public static void main(String[] args) throws Exception {
        Class.forName(DRIVER_CLASS);
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement statement = conn.createStatement();
//        //创建表
//        statement.execute("DROP TABLE IF EXISTS USER_INF");
//        statement.execute("CREATE TABLE USER_INF(id INTEGER PRIMARY KEY ,name VARCHAR(100), sex VARCHAR(2))");
//
//        //插入数据
//        statement.executeUpdate("INSERT INTO USER_INF VALUES(1, 'tom', '男') ");
//        statement.executeUpdate("INSERT INTO USER_INF VALUES(2, 'jack', '女') ");
//        statement.executeUpdate("INSERT INTO USER_INF VALUES(3, 'marry', '男') ");
//        statement.executeUpdate("INSERT INTO USER_INF VALUES(4, 'lucy', '男') ");
        //查询数据
        ResultSet resultSet = statement.executeQuery("select * from USER_INF");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("name") +
                    ", " + resultSet.getString("sex"));
        }

        statement.close();
        conn.close();
    }
}
