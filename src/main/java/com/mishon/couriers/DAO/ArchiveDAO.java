package com.mishon.couriers.DAO;

import com.mishon.couriers.Constants;
import com.mishon.couriers.DataConnection;
import com.mishon.couriers.Entities.Archive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArchiveDAO {
    final static Logger logger = LogManager.getLogger("com.zetcode");

    public List<Archive> getArchiveFromDB(int id, int route, int carid, int income,
                                   String date, int expense, String completionMessage ) throws SQLException{
        List<Archive> getArr = new ArrayList<Archive>();
        Connection conn = DataConnection.getDBConnection();

        final String SQLQuery = String.format(
                "SELECT * FROM archive WHERE idarchive %s AND route_taken %s AND car_used %s AND income %s AND date %s AND expense %s AND completed %s",
                id== Constants.SELECT_ALL_INT ? "LIKE '%'":"= " + id,
                route== Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + route,
                carid== Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + carid,
                income== Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + income,
                date.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + date + "'",
                expense== Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + expense,
                completionMessage.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= " + completionMessage);




    PreparedStatement stat = conn.prepareStatement(SQLQuery);
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
return getArr;
}

public void addArchiveToDB(String route, String car, String income, String date, String expense, String completed) throws SQLException{
    DataConnection dConn = new DataConnection();
    Connection conn = dConn.getDBConnection();

    String SQLQuery = String.format(
            "INSERT INTO archive (route_taken, car_used, income, date, expense, completed) VALUES ('%s', '%s', '%s', '%s','%s','%s')",
           route,
            car,
            income,
            date,
            expense,
            completed);

        PreparedStatement stat = conn.prepareStatement(SQLQuery);
        stat.executeUpdate(SQLQuery);
        conn.close();

}
    }


