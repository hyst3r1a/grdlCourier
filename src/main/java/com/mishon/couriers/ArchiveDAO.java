package com.mishon.couriers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

class ArchiveDAO {
    final static Logger logger = LogManager.getLogger("com.zetcode");
    ArrayList<Archive> getArchiveFromDB(int id, int route, int carid, int income,
                                        String date, int expense, String completionMessage ){
        ArrayList<Archive> getArr = new ArrayList<Archive>();
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();

        String SQLQuery = "SELECT * FROM archive WHERE";
        if(id == -1){
            SQLQuery += " idarchive LIKE '%' AND";
        }else{
            SQLQuery +="idarchive=" + id + "AND";
        }

        if(route == -1){
            SQLQuery += " route_taken LIKE '%' AND";
        }else{
            SQLQuery +=" route_taken=" + route + "AND";
        }

        if(carid == -1){
            SQLQuery += " car_used LIKE '%' AND";
        }else{
            SQLQuery +=" car_used=" + carid + "AND";
        }

        if(income == -1){
            SQLQuery += " income LIKE '%' AND";
        }else{
            SQLQuery +=" income=" + income + "AND";
        }

        if(date.equals("")){
            SQLQuery += " date LIKE '%' AND";
        }else{
            SQLQuery +=" date=" + date + "AND";
        }

        if(expense == -1){
            SQLQuery += " expense LIKE '%' AND";
        }else{
            SQLQuery +=" expense=" + expense + "AND" ;
        }

        if(completionMessage.equals("")){
            SQLQuery += " completed LIKE '%'";
        }else{
            SQLQuery +=" completed=" + completionMessage ;
        }
try {

    Statement stat = conn.createStatement();
    ResultSet rs = stat.executeQuery(SQLQuery);

    while (rs.next()) {
        Archive temp = new Archive();
        temp.setId(rs.getInt("idarchive"));
        temp.setRouteId(rs.getInt("route_taken"));
        temp.setCarId(rs.getInt("car_used"));
        temp.setIncome(rs.getInt("income"));
        temp.setDate(rs.getString("date"));
        temp.setExpense(rs.getInt("expense"));
        temp.setCompletionMessage(rs.getString("completed"));

        getArr.add(temp);
    }
    conn.close();
}catch(SQLException e) {
    logger.info(e.toString());
}

return getArr;
}
public void addArchiveToDB(String route, String car, String income, String date, String expense, String completed){
    DataConnection dConn = new DataConnection();
    Connection conn = dConn.getDBConnection();
    String SQLQuery = "INSERT INTO archive (route_taken, car_used, income, date, expense, completed) VALUES ('" +
           route + "', '" + car + "', '" + income + "', '" + date + "', '" + expense + "', '" + completed + "');";
    try {
        Statement stat = conn.createStatement();
        stat.executeUpdate(SQLQuery);
        conn.close();
    } catch (SQLException e) {
         System.out.println("Corrupted SQL 'INSERT INTO' query(Request): " + SQLQuery + "\n Stack trace: ");
        logger.info(e.toString());
    }
}
    }


