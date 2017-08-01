package cn.com.benic.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/6/21.
 */
public class ConnectionUtil {

    private static Connection conn=null;

    public static Connection getConn(){
        try{
            if(conn == null){
                synchronized(Connection.class) {
                     if (conn == null) {
                         //加载属性文件，读取数据库连接配置信息
                         Properties pro = new Properties();
                         try {
                             pro.load(ConnectionUtil.class.getResourceAsStream("/jdbc.properties"));

                             String driverClassName = pro.getProperty("jdbc.driverClassName");
                             String url = pro.getProperty("jdbc.url");
                             String user = pro.getProperty("jdbc.user");
                             String password = pro.getProperty("jdbc.password");

                             Class.forName(driverClassName);
                             conn = DriverManager.getConnection(url, user, password);
                         } catch (IOException e) {
                             System.out.println("未找到配置文件！！！");
                         }
                     }
                     return conn;
                }
            } else {
                return conn;
            }

        }catch(ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void end(){
        try{
            if(conn != null){

                conn.close();
                conn = null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
