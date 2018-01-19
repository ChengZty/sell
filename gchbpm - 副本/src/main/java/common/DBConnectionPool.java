package common;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
 
/**
 * 连接数据库的工具类,被定义成不可继承且是私有访问
 */
public final class DBConnectionPool {
 private static ResourceBundle rb = ResourceBundle.getBundle("dbconfig");
 private static String url = rb.getObject("jdbc.master.url").toString();//"jdbc:mysql://192.168.1.33:3306/gchbpm?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
 private static String user = rb.getObject("jdbc.master.username").toString();//"gch";
 private static String psw = rb.getObject("jdbc.master.password").toString();//"asd12345";


 static {
  try {
   Class.forName("com.mysql.jdbc.Driver");
  } catch (ClassNotFoundException e) {
   e.printStackTrace();
   throw new RuntimeException(e);
  }
 }

 private DBConnectionPool() {

 }

 /**
  * 获取数据库的连接
  * @return conn
  */
 public static Connection getConnection() {
	 Connection conn = null;
   try {
    conn = DriverManager.getConnection(url, user, psw);
   } catch (SQLException e) {
    e.printStackTrace();
    throw new RuntimeException(e);
   }
  return conn;
 }

 /**
  * 释放资源
  * @param conn
  * @param pstmt
  * @param rs
  */
 public static void closeResources(Connection conn,PreparedStatement pstmt,ResultSet rs) {
  if(null != rs) {
   try {
    rs.close();
   } catch (SQLException e) {
    e.printStackTrace();
    throw new RuntimeException(e);
   } finally {
    if(null != pstmt) {
     try {
      pstmt.close();
     } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
     } finally {
      if(null != conn) {
       try {
        conn.close();
       } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
       }
      }
     }
    }
   }
  }
 }
}