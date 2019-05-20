package com.mishon.couriers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class CarDAO {
    final static Logger logger = LogManager.getLogger("com.zetcode");
    ArrayList<Car> getCarFromDB(int id, String car, String type, int driver,
                                int capacity, int repairs) {
        ArrayList<Car> getArr = new ArrayList<Car>();
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = "SELECT * FROM cars WHERE";
        if (id == -1) {
            SQLQuery += " idcars LIKE '%' AND";
        } else {
            SQLQuery += " idcars=" + id + "AND";
        }
        if (car.equals("")) {
            SQLQuery += " car LIKE '%' AND";
        } else {
            SQLQuery += " car=" + car + "AND";
        }

        if (type.equals("")) {
            SQLQuery += " type LIKE '%' AND";
        } else {
            SQLQuery += " type=" + type + "AND";
        }

        if (driver == -1) {
            SQLQuery += " driver LIKE '%' AND";
        } else {
            SQLQuery += " driver=" + driver + " AND";
        }

        if (capacity == -1) {
            SQLQuery += " m3 LIKE '%' AND";
        } else {
            SQLQuery += " m3>" + (capacity - 1) + " AND";
        }

        if (repairs == -1) {
            SQLQuery += " needRepairs LIKE '%'";
        } else {
            SQLQuery += " needRepairs=" + repairs;
        }

//System.out.println(SQLQuery);
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(SQLQuery);

            while (rs.next()) {
                Car temp = new Car();
                temp.setId(rs.getInt("idcars"));
                temp.setCarName(rs.getString("car"));
                temp.setCarType(rs.getString("type"));
                temp.setDriver(rs.getInt("driver"));
                temp.setCapacity(rs.getInt("m3"));
                temp.setNeedRepairs(rs.getInt("needRepairs"));


                getArr.add(temp);
            }
            conn.close();
        } catch (SQLException e) {
            logger.info(e.toString());
        }
        return getArr;
    }

    public void fixCar(int carId) {
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = "UPDATE cars SET needRepairs=0 WHERE idcars=" + carId;
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate(SQLQuery);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Corrupted SQL 'UPDATE' query(Car): " + SQLQuery + "\n Stack trace: ");
            logger.info(e.toString());
        }
    }
    public void breakCar(int carId, int expense) {
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = "UPDATE cars SET needRepairs="+ expense+" WHERE idcars=" + carId;
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate(SQLQuery);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Corrupted SQL 'UPDATE' query(Car): " + SQLQuery + "\n Stack trace: ");
            logger.info(e.toString());
        }
    }
}
