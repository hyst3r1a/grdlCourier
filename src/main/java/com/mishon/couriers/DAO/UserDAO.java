package com.mishon.couriers.DAO;

import com.mishon.couriers.*;
import com.mishon.couriers.DataConnection;
import com.mishon.couriers.Entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    final static Logger logger = LogManager.getLogger("com.zetcode");
    public List<User> getUserFromDB(int id, String name, String type, String pass ) throws SQLException{
        List<User> getArr = new ArrayList<User>();
        DataConnection dConn = new DataConnection();

        Connection conn = dConn.getDBConnection();

        final String SQLQuery = String.format(
                "SELECT * FROM users WHERE idusers %s AND name %s AND type %s AND pass %s",
                id== Constants.SELECT_ALL_INT ? "LIKE '%'":"= " + id,
                name.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + name + "'",
                type.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + type + "'",
                pass.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + pass + "'");


System.out.println(SQLQuery);
        PreparedStatement stat = conn.prepareStatement(SQLQuery);
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

            return getArr;

    }

}
