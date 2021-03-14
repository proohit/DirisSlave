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

    public static Song getSongById(int id) {
        Song song = null;
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Record result = create.select().from(table(TABLE_NAME)).where(field(FIELD_ID).eq(id)).fetchOne();
            if (result != null) {
                song = new Song(result.get(FIELD_ID, Integer.class), result.get(FIELD_TITLE, String.class),
                        result.get(FIELD_URL, String.class));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return song;
    }

    public static void insertSong(Song song) {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            create.insertInto(table(TABLE_NAME), field(FIELD_TITLE), field(FIELD_URL))
                    .values(song.getTitle().replace("'", "''"), song.getUrl()).execute();
            song.setId(create.lastID().intValue());
        } catch (SQLException e) {
            Logger.error(e);
        }
    }

    public static List<Song> getSongsByTitle(String title) {
        List<Song> songs = new ArrayList<>();
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Result<Record> result = create.select().from(table(TABLE_NAME)).where(field(FIELD_TITLE).eq(title)).fetch();
            for (Record record : result) {
                songs.add(new Song(record.get(FIELD_ID, Integer.class), record.get(FIELD_TITLE, String.class),
                        record.get(FIELD_URL, String.class)));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return songs;
    }

    public static List<Song> getSongsByUrl(String url) {
        ArrayList<Song> songs = new ArrayList<>();
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Result<Record> result = create.select().from(table(TABLE_NAME)).where(field(FIELD_URL).eq(url)).fetch();
            for (Record record : result) {
                songs.add(new Song(record.get(FIELD_ID, Integer.class), record.get(FIELD_TITLE, String.class),
                        record.get(FIELD_URL, String.class)));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return songs;
    }

    public static boolean hasSong(String uri) {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Record result = create.select().from(table(TABLE_NAME)).where(field(FIELD_URL).eq(uri)).fetchOne();
            return result != null;
        } catch (SQLException e) {
            Logger.error(e);
        }
        return false;
    }
}
