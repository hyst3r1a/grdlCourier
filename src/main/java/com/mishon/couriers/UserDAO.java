package com.mishon.couriers;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class UserDAO {
    ArrayList<User> getUserFromDB(int id, String name, String type, String pass ){
        ArrayList<User> getArr = new ArrayList<User>();
        DataConnection dConn = new DataConnection();

        Connection conn = dConn.getDBConnection();
       /* try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/appname");
            Connection conn = ds.getConnection();*/
            String SQLQuery = "SELECT * FROM users WHERE";
            if (id == -1) {
                SQLQuery += " idusers LIKE '%' AND";
            } else {
                SQLQuery += "idusers=" + id + " AND";
            }
            if (name.equals("")) {
                SQLQuery += " name LIKE '%' AND";
            } else {
                SQLQuery += " name='" + name + "' AND";
            }
            if (type.equals("")) {
                SQLQuery += " type LIKE '%' AND";
            } else {
                SQLQuery += " type='" + type + "' AND";
            }
            if (pass.equals("")) {
                SQLQuery += " pass LIKE '%'";
            } else {
                SQLQuery += " pass='" + pass + "'";
            }
            try {
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(SQLQuery);

                while (rs.next()) {
                    User temp = new User();
                    temp.setId(rs.getInt("idusers"));
                    temp.setName(rs.getString("name"));
                    temp.setType(rs.getString("type"));
                    temp.setPass(rs.getString("pass"));

                    getArr.add(temp);
                }
                conn.close();
            } catch (SQLException e) {
            }//}catch(Exception e){e.printStackTrace();}
            return getArr;

    }

}
