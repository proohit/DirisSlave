package database;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.INTEGER;
import static org.jooq.impl.SQLDataType.VARCHAR;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.CreateTableColumnStep;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.tinylog.Logger;

public class SongTable {

    private SongTable() {
    }

    private static final String FIELD_URL = "url";
    private static final String FIELD_TITLE = "title";
    public static final String FIELD_ID = "id";
    static final String TABLE_NAME = "songs";

    public static void createTable() {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            try (CreateTableColumnStep table = create.createTable(TABLE_NAME)) {
                table.column(FIELD_ID, INTEGER.identity(true)).column(FIELD_URL, VARCHAR).column(FIELD_TITLE, VARCHAR)
                        .constraints(primaryKey(FIELD_ID)).execute();
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
    }

    public static List<Song> getAllSongs() {
        ArrayList<Song> result = new ArrayList<>();
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM songs;");
            while (rs.next()) {
                result.add(new Song(rs.getInt("id"), rs.getString("title"), rs.getString("url")));
            }
            return result;
        } catch (SQLException e) {
            if (!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return null;
    }

    public static void insertSong(Song song) {
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO songs(title,url) VALUES('" + song.getTitle().replace("'", "''") + "','" + song.getUrl() + "')", Statement.RETURN_GENERATED_KEYS);
            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) song.setId(key.getInt(1));
        } catch (SQLException e) {
            if (!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
    }

    public static List<Song> getSongsByTitle(String title) {
        ArrayList<Song> result = new ArrayList<>();
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM songs WHERE title=\'" + title + "\'");
            while (rs.next()) {
                result.add(new Song(rs.getInt("id"), rs.getString("title"), rs.getString("url")));
            }
        } catch (SQLException e) {
            if (!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return result;
    }

    public static List<Song> getSongsByUrl(String url) {
        ArrayList<Song> result = new ArrayList<>();
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM songs WHERE url=\'" + url + "\'");
            while (rs.next()) {
                result.add(new Song(rs.getInt("id"), rs.getString("title"), rs.getString("url")));
            }
        } catch (SQLException e) {
            if (!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return result;
    }

    public static boolean hasSong(String uri) {
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM songs WHERE url=\'" + uri + "\'");
            if (rs.next()) return true;
        } catch (SQLException e) {
            if (!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return false;
    }
}
