package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import main.ReadPropertyFile;

public class DBManager {
    private static Connection conn = null;
    private static boolean debug = true;

    public static Connection connect() {
        ReadPropertyFile rpf = ReadPropertyFile.getInstance();

        if (conn == null) {
            try {
                String url = String.format(
                        "jdbc:mysql://%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                        rpf.getDbHost(), rpf.getDbDatabase());
                conn = DriverManager.getConnection(url, rpf.getDbUser(), rpf.getDbPassword());
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
