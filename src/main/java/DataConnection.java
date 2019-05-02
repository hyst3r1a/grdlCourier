import java.sql.*;


public class DataConnection {
    Connection dbConnection;
    Statement statement;

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_courier?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","phantasm322");
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }


    public String testGet() {
        String selectTableSQL = "SELECT idusers, name, type from users";
        String userid = "heh";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выбираем данные с БД
            ResultSet rs = statement.executeQuery(selectTableSQL);

            // И если что то было получено то цикл while сработает
            while (rs.next()) {
                userid = rs.getString("idusers");
                String username = rs.getString("name");
                String type = rs.getString("type");

                System.out.println("userid : " + userid);
                System.out.println("username : " + username);
                System.out.println("email : " + type);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return userid;
    }
}
