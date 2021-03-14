package database;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, SQLDialect.MYSQL);
            Result<Record> result = create.select().from(table(TABLE_NAME)).fetch();
            for (Record record : result) {
                playlists.add(new Playlist(record.get(field(FIELD_NAME, String.class))));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return playlists;
    }

    public static int deletePlaylist(String playlist) {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, SQLDialect.MYSQL);
            return create.deleteFrom(table(TABLE_NAME)).where(field(FIELD_NAME).eq(playlist)).execute();
        } catch (SQLException e) {
            Logger.error(e);
        }
        return 0;
    }

    public static Playlist createPlaylist(String name) {
        Playlist createdPlaylist = null;
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            create.insertInto(table(TABLE_NAME), field(FIELD_NAME)).values(name).execute();
            createdPlaylist = getPlaylist(name);
        } catch (SQLException e) {
            Logger.error(e);
        }

        return createdPlaylist;
    }

    public static Playlist getPlaylist(String name) {
        Playlist playlist = null;
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Record result = create.select().from(table(TABLE_NAME)).where(field(FIELD_NAME).eq(name)).fetchOne();
            if (result != null) {
                playlist = new Playlist(result.get(FIELD_NAME, String.class));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return playlist;
    }
}
