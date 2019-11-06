package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    static Connection conn = null;

    public static Connection connect() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/direnc_discord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                conn = DriverManager.getConnection(url, "direnc", "Elasus!834679");
                System.out.println("Connection to DB established.");
                return conn;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("retrying connection...");
                return connect();
            }
        } else {
            conn = null;
            return connect();
        }
    }

    public static boolean connected() {
        return conn != null;
    }

    public static Connection getConnection() {
        if (connected()) return conn;
        else return null;
    }
}
