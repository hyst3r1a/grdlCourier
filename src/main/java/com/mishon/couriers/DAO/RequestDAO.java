package com.mishon.couriers.DAO;

import com.mishon.couriers.Constants;
import com.mishon.couriers.DataConnection;
import com.mishon.couriers.Entities.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    final static Logger logger = LogManager.getLogger("com.zetcode");
    public List<Request> getRequestFromDB(int id, String issuer, String date, int capacity,
                                  int price, int car, int route ) throws SQLException{
        List<Request> getArr = new ArrayList<Request>();
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();

        final String SQLQuery = String.format(
                "SELECT * FROM requests WHERE idrequests %s AND issuer %s AND date %s AND req_capacity %s AND price %s AND car %s AND route %s",
                id== Constants.SELECT_ALL_INT ? "LIKE '%'":"= " + id,
                issuer.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + issuer + "'",
                date.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + date + "'",
                capacity== Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + capacity,
                price== Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + price,
                car== Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + car,
                route== Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + route);



        PreparedStatement stat = conn.prepareStatement(SQLQuery);
            ResultSet rs = stat.executeQuery(SQLQuery);

            while (rs.next()) {
                Request temp = new Request();
                temp.setId(rs.getInt("idrequests"));
                temp.setIssuer(rs.getString("issuer"));
                temp.setDate(rs.getString("date"));
                temp.setReqCapacity(rs.getInt("req_capacity"));
                temp.setPrice(rs.getInt("price"));
                temp.setCar(rs.getInt("car"));
                temp.setRoute(rs.getInt("route"));

                getArr.add(temp);
            }conn.close();

        return getArr;
    }
    public void addRequestToDB(String issuer, String date, String capacity, String price, String car, String route) throws SQLException{
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = String.format("INSERT INTO requests (issuer, date, req_capacity, price, car, route) VALUES ('%s','%s','%s','%s','%s','%s')",
                issuer,
        date,
        capacity,
        price,
        car,
        route);

            PreparedStatement stat = conn.prepareStatement(SQLQuery);
            stat.executeUpdate(SQLQuery);
            conn.close();

    }
    public void updateRequestAssignment(String newCar, String reqId) throws SQLException{
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = String.format("UPDATE requests SET car=%s WHERE idrequests=%s",
                newCar,
                reqId);
        System.out.println(SQLQuery);

            PreparedStatement stat = conn.prepareStatement(SQLQuery);
            stat.executeUpdate(SQLQuery);
            conn.close();

    }
    public void removeRequest(String reqId) throws SQLException{
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = String.format("DELETE FROM requests WHERE idrequests=%s", reqId);
        System.out.println(SQLQuery);

            PreparedStatement stat = conn.prepareStatement(SQLQuery);
            stat.executeUpdate(SQLQuery);
            conn.close();

    }
}
