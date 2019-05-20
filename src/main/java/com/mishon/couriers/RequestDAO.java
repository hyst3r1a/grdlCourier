package com.mishon.couriers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class RequestDAO {
    final static Logger logger = LogManager.getLogger("com.zetcode");
    ArrayList<Request> getRequestFromDB(int id, String issuer, String date, int capacity,
                                        int price, int car, int route ){
        ArrayList<Request> getArr = new ArrayList<Request>();
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = "SELECT * FROM requests WHERE";
        if(id == -1){
            SQLQuery += " idrequests LIKE '%' AND";
        }else{
            SQLQuery +=" idrequests=" + id + " AND";
        }
        if(issuer.equals("")){
            SQLQuery += " issuer LIKE '%' AND";
        }else{
            SQLQuery +=" issuer=" + issuer + " AND";
        }
        if(date.equals("")){
            SQLQuery += " date LIKE '%' AND";
        }else{
            SQLQuery +=" date=" + date + " AND";
        }
        if(capacity == -1){
            SQLQuery += " req_capacity LIKE '%' AND";
        }else{
            SQLQuery +=" req_capacity=" + capacity + " AND";
        }
        if(price == -1){
            SQLQuery += " price LIKE '%' AND";
        }else{
            SQLQuery +=" price=" + price + " AND";
        }
        if(car == -1){
            SQLQuery += " car LIKE '%' AND";
        }else{
            SQLQuery += " car=" + car + " AND";
        }
        if(route == -1){
            SQLQuery += " route LIKE '%'";
        }else{
            SQLQuery +=" route=" + route;
        }
       // System.out.println(SQLQuery);
        try {

            Statement stat = conn.createStatement();
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
        }catch(SQLException e) {
            logger.info(e.toString());
        }
        return getArr;
    }
    public void addRequestToDB(String issuer, String date, String capacity, String price, String car, String route) {
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = "INSERT INTO requests (issuer, date, req_capacity, price, car, route) VALUES ('" +
                issuer + "', '" + date + "', '" + capacity + "', '" + price + "', '" + car + "', '" + route + "');";
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate(SQLQuery);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Corrupted SQL 'INSERT INTO' query(Request): " +  SQLQuery + "\n Stack trace: ");
            logger.info(e.toString());
        }
    }
    public void updateRequestAssignment(String newCar, String reqId){
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = "UPDATE requests SET car=" + newCar +" WHERE idrequests=" + reqId;
        System.out.println(SQLQuery);
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate(SQLQuery);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Corrupted SQL 'UPDATE' query(Request): " +  SQLQuery + "\n Stack trace: ");
            logger.info(e.toString());
        }
    }
    public void removeRequest(String reqId){
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = "DELETE FROM requests WHERE idrequests=" + reqId;
        System.out.println(SQLQuery);
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate(SQLQuery);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Corrupted SQL 'DELETE' query(Request): " +  SQLQuery + "\n Stack trace: ");
            logger.info(e.toString());
        }
    }
}
