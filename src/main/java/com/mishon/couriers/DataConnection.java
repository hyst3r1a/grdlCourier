package com.mishon.couriers;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;


class DataConnection {


    static Connection getDBConnection() {
/*
        Connection dbConnection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_courier?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","phantasm322");
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
*/
          try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/appname");
            Connection conn = ds.getConnection();
            return conn;
        }catch(Exception e){e.printStackTrace();
         }
return null;
    }



}
