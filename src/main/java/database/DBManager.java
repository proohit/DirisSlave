package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static Connection conn = null;
    private static boolean debug = true;

    public static Connection connect() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/direnc_discord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                conn = DriverManager.getConnection(url, "direnc", "Elasus!834679");
                if (debug)
                    System.out.println("Connection to DB established.");
                return conn;
            } catch (SQLException e) {
                e.printStackTrace();
                if (debug)
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

    public static void setDebug(boolean status) {
        debug = status;
    }

    public static Connection getConnection() {
        if (connected())
            return conn;
        else
            return null;
    }
}
