package com.mishon.couriers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class RouteDAO {
    final static Logger logger = LogManager.getLogger("com.zetcode");
    List<Route> getRouteFromDB(int id, String destination, String cargo ) throws SQLException{
        List<Route> getArr = new ArrayList<Route>();
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();

        final String SQLQuery = String.format(
                "SELECT * FROM routes WHERE idroutes=%s AND destination=%s AND cargo=%s",
                id== Constants.SELECT_ALL_INT ? "LIKE '%'":"= " + id,
                destination.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= " + destination,
                cargo.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= " + cargo);








            Statement stat = conn.prepareStatement(SQLQuery);
            ResultSet rs = stat.executeQuery(SQLQuery);

            while (rs.next()) {
                Route temp = new Route();
                temp.setId(rs.getInt("idroutes"));
                temp.setDestination(rs.getString("destination"));
                temp.setCargo(rs.getString("cargo"));

                getArr.add(temp);
            }conn.close();

        return getArr;
    }
}
