package com.mishon.couriers;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;


public class DataConnection {


    public static Connection getDBConnection() {

          try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/appname");
            Connection conn = ds.getConnection();
            return conn;
        }catch(Exception e){
              e.printStackTrace();
         }
return null;
    }



}
