package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.jooq.CreateTableColumnStep;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.tinylog.Logger;

import static org.jooq.impl.SQLDataType.*;
import static org.jooq.impl.DSL.*;

public class SongHistoryTable {

    private SongHistoryTable() {
    }

    static final String TABLE_NAME = "history_songs";
    private static final String FIELD_TIMESTAMP = "timestamp";
    private static final String FIELD_SONGID = "songId";
    private static final String FIELD_HISTORYID = "historyId";

    public static void createTable() {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            try (CreateTableColumnStep table = create.createTable(TABLE_NAME)) {
                table.column(FIELD_HISTORYID, INTEGER.identity(true)).column(FIELD_SONGID, INTEGER)
                        .column(FIELD_TIMESTAMP, TIMESTAMP)
                        .constraints(primaryKey(FIELD_HISTORYID),
                                foreignKey(FIELD_SONGID).references(SongTable.TABLE_NAME, SongTable.FIELD_ID))
                        .execute();
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
    }

    public static Map<Song, Integer> getSongStatistics() {
        Map<Song, Integer> statistics = new HashMap<>();
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
        try {
            DBManager.setDebug(false);
            con = DBManager.connect();
            stmt = con.createStatement();
            rs = stmt.executeQuery(
                    "SELECT count(songId),songId, songs.title FROM `HistorySongs`,`songs` where HistorySongs.songId = songs.id group by songId order by count(songId) desc limit 10");
            while (rs.next()) {
                statistics.put(SongTable.getSongById(rs.getInt("songId")), rs.getInt("count(songId)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                rs.close();
                stmt.close();
                DBManager.setDebug(true);
            } catch (SQLException e) {
            }
        }
        return statistics;
    }

    public static Map<Integer, Song> getLastSongs() {
        Map<Integer, Song> songs = new HashMap<>();

        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Song_history order by timestamp desc LIMIT 10");
            while (rs.next()) {
                Song song = SongTable.getSongById(rs.getInt("songId"));
                song.setTimestamp(rs.getTimestamp("timestamp").toString());
                songs.put(rs.getInt("historyId"), song);
            }
        } catch (SQLException e) {
            if (!DBManager.connected())
                DBManager.connect();
            e.printStackTrace();
        }
        return songs;
    }

    public static void insertHistoryItem(Song song) {
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO Song_history(songId) VALUES (" + song.getId() + ")");

        } catch (SQLException e) {
            if (!DBManager.connected())
                DBManager.connect();
            e.printStackTrace();
        }
    }

    public static boolean hasEntry(Song song) {
        boolean hasEntry = false;
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            if (stmt.executeQuery("SELECT * FROM Song_history where songId=" + song.getId()).next())
                hasEntry = true;

        } catch (SQLException e) {
            if (!DBManager.connected())
                DBManager.connect();
            e.printStackTrace();
        }
        return hasEntry;
    }

    public static void updateTimestamp(Song song) {
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE Song_history set timestamp = CURRENT_TIMESTAMP WHERE songId=" + song.getId());
        } catch (SQLException e) {
            if (!DBManager.connected())
                DBManager.connect();
            e.printStackTrace();
        }
    }
}
