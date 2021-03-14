package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.jooq.CreateTableColumnStep;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.tinylog.Logger;

import static org.jooq.impl.SQLDataType.*;
import static org.jooq.impl.DSL.*;

public class SongHistoryTable {

    private SongHistoryTable() {
    }

    static final String TABLE_NAME = "Song_history";
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
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Result<Record3<Integer, Object, Object>> result = create
                    .select(count(field(FIELD_SONGID)), field(FIELD_SONGID), field(SongTable.FIELD_TITLE))
                    .from(table(TABLE_NAME), table(SongTable.TABLE_NAME))
                    .where(field(FIELD_SONGID).eq(field(SongTable.FIELD_ID))).groupBy(field(FIELD_SONGID))
                    .orderBy(count(field(FIELD_SONGID)).desc()).limit(10).fetch();
            for (var record : result) {
                statistics.put(SongTable.getSongById(record.get(FIELD_SONGID, Integer.class)),
                        record.get(count(field(FIELD_SONGID, Integer.class))));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return statistics;
    }

    public static Map<String, Song> getLastSongs() {
        Map<String, Song> songs = new HashMap<>();
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Result<Record> result = create.select().from(table(TABLE_NAME)).orderBy(field(FIELD_TIMESTAMP).desc())
                    .limit(10).fetch();
            for (var record : result) {
                Song song = SongTable.getSongById(record.get(FIELD_SONGID, Integer.class));
                song.setTimestamp(record.get(FIELD_TIMESTAMP, String.class));
                songs.put(record.get(FIELD_TIMESTAMP, String.class), song);
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return songs;
    }

    public static void insertHistoryItem(Song song) {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            create.insertInto(table(TABLE_NAME), field(FIELD_SONGID)).values(song.getId()).execute();
        } catch (SQLException e) {
            Logger.error(e);
        }
    }

}
