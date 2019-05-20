package com.mishon.couriers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class RouteDAO {
    final static Logger logger = LogManager.getLogger("com.zetcode");
    ArrayList<Route> getRouteFromDB(int id, String destination, String cargo ){
        ArrayList<Route> getArr = new ArrayList<Route>();
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = "SELECT * FROM routes WHERE";
        if(id == -1){
            SQLQuery += " idroutes LIKE '%' AND";
        }else{
            SQLQuery +=" idroutes=" + id + "AND";
        }

        if(destination.equals("")){
            SQLQuery += " destination LIKE '%' AND";
        }else{
            SQLQuery +=" destination=" + destination +"AND";
        }
        if(cargo.equals("")){
            SQLQuery += " cargo LIKE '%'";
        }else{
            SQLQuery +=" cargo=" + cargo ;
        }
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(SQLQuery);

            while (rs.next()) {
                Route temp = new Route();
                temp.setId(rs.getInt("idroutes"));
                temp.setDestination(rs.getString("destination"));
                temp.setCargo(rs.getString("cargo"));

                getArr.add(temp);
            }conn.close();
        }catch(SQLException e) {
            logger.info(e.toString());
        }
        return getArr;
    }
}
