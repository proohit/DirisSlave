package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

import org.jooq.CreateTableColumnStep;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.tinylog.Logger;

public class PlaylistTable {

    private PlaylistTable() {
    }

    public static final String FIELD_NAME = "name";
    public static final String TABLE_NAME = "playlist";

    public static void createTable() {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, SQLDialect.MYSQL);
            try (CreateTableColumnStep table = create.createTable(TABLE_NAME)) {
                table.column(FIELD_NAME, VARCHAR(255)).constraints(primaryKey(FIELD_NAME)).execute();
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
    }
            }
        } catch (SQLException e) {
            if (!DBManager.connected())
                DBManager.connect();
            e.printStackTrace();
        }
        return playlists;
    }

    public static int deletePlaylist(String playlist) {
        try {
            Connection con = DBManager.connect();
            Statement stmt = con.createStatement();
            return stmt.executeUpdate("DELETE FROM playlist WHERE name='" + playlist + "'");

        } catch (SQLException e) {
            if (!DBManager.connected())
                DBManager.connect();
            e.printStackTrace();
        }
        return 0;
    }

    public static Playlist createPlaylist(String name) {
        Playlist createdPlaylist = null;
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO playlist(name) VALUES('" + name + "')");
            createdPlaylist = getPlaylist(name);

        } catch (SQLException e) {
            if (!DBManager.connected())
                DBManager.connect();
            e.printStackTrace();
        }

        return createdPlaylist;
    }

    public static Playlist getPlaylist(String name) {
        Playlist playlist = null;
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM playlist WHERE name='" + name + "'");
            if (rs.next())
                playlist = new Playlist(rs.getString("name"));
        } catch (SQLException e) {
            if (!DBManager.connected())
                DBManager.connect();
            e.printStackTrace();
        }
        return playlist;
    }
}
