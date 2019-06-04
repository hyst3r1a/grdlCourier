package com.mishon.couriers.DAO;

import com.mishon.couriers.Constants;
import com.mishon.couriers.DataConnection;
import com.mishon.couriers.Entities.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    final static Logger logger = LogManager.getLogger("com.zetcode");
    public List<Car> getCarFromDB(int id, String car, String type, int driver,
                           int capacity, int repairs)  throws SQLException{
        List<Car> getArr = new ArrayList<Car>();
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        final String SQLQuery = String.format(
                "SELECT * FROM cars WHERE idcars %s AND car %s AND type %s AND driver %s AND m3 %s AND needRepairs %s",
                id== Constants.SELECT_ALL_INT ? "LIKE '%'":"= " + id,
                car.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + car + "'",
                type.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + type + "'",
                driver== Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + driver,
                capacity== Constants.SELECT_ALL_INT ? "LIKE '%'" : ">= " + capacity,
                repairs== Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + repairs);


            PreparedStatement stat = conn.prepareStatement(SQLQuery);
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

        return getArr;
    }

    public void fixCar(int carId) throws SQLException{
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = String.format("UPDATE cars SET needRepairs=0 WHERE idcars= %s", carId);

            PreparedStatement stat = conn.prepareStatement(SQLQuery);
            stat.executeUpdate(SQLQuery);
            conn.close();

    }
    public void breakCar(int carId, int expense) throws SQLException {
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getDBConnection();
        String SQLQuery = String.format("UPDATE cars SET needRepairs=%s WHERE idcars=%s",expense, carId);

            PreparedStatement stat = conn.prepareStatement(SQLQuery);
            stat.executeUpdate(SQLQuery);
            conn.close();

    }
}
