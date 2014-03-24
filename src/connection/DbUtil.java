package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtil {
	private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
            	Class.forName("com.mysql.jdbc.Driver");
    			connection = DriverManager
    				      .getConnection("jdbc:mysql://localhost:3306/couponmarket?"
    				          + "user=root&password=");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}
