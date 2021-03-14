package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.tinylog.Logger;

import main.ReadPropertyFile;

public class DBManager {

    private DBManager() {
    }

    public static final SQLDialect DEFAULT_DIALECT = SQLDialect.MYSQL;
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        ReadPropertyFile rpf = ReadPropertyFile.getInstance();
        config.setJdbcUrl(String.format(
                "jdbc:mysql://%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                rpf.getDbHost(), rpf.getDbDatabase()));
        config.setUsername(rpf.getDbUser());
        config.setPassword(rpf.getDbPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void initializeDatabase() {
        try (Connection con = getConnection()) {
            List<String> targetTables = new ArrayList<>(Arrays.asList(PlaylistTable.TABLE_NAME, SongTable.TABLE_NAME,
                    SongPlaylistTable.TABLE_NAME, SongHistoryTable.TABLE_NAME));
            List<Table<?>> tables = DSL.using(con, DEFAULT_DIALECT).meta().getTables();
            for (Table<?> table : tables) {
                if (targetTables.contains(table.getName())) {
                    targetTables.remove(table.getName());
                }
            }
            if (!targetTables.isEmpty()) {
                Logger.info("Following Tables are missing: {}", targetTables.toString());
                if (targetTables.contains(PlaylistTable.TABLE_NAME)) {
                    PlaylistTable.createTable();
                }
                if (targetTables.contains(SongTable.TABLE_NAME)) {
                    SongTable.createTable();
                }
                if (targetTables.contains(SongPlaylistTable.TABLE_NAME)) {
                    SongPlaylistTable.createTable();
                }
                if (targetTables.contains(SongHistoryTable.TABLE_NAME)) {
                    SongHistoryTable.createTable();
                }
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
    }
}
