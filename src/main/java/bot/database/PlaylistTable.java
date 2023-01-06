package bot.database;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.primaryKey;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.SQLDataType.BIGINT;
import static org.jooq.impl.SQLDataType.VARCHAR;

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
    public static final String FIELD_GUILDID = "guildid";
    public static final String TABLE_NAME = "playlist";

    public static void createTable() {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, SQLDialect.MYSQL);
            try (CreateTableColumnStep table = create.createTable(TABLE_NAME)) {
                table.column(FIELD_NAME, VARCHAR(255)).column(FIELD_GUILDID, BIGINT)
                        .constraints(primaryKey(FIELD_NAME, FIELD_GUILDID)).execute();
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
    }

    public static List<Playlist> getPlaylists(long guildId) {
        List<Playlist> playlists = new ArrayList<>();
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, SQLDialect.MYSQL);
            Result<Record> result = create.select().from(table(TABLE_NAME)).where(field(FIELD_GUILDID).eq(guildId))
                    .fetch();
            for (Record record : result) {
                playlists.add(new Playlist(record.get(field(FIELD_NAME, String.class)),
                        record.get(field(FIELD_GUILDID, Long.class))));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return playlists;
    }

    public static int deletePlaylist(String playlist, long guildId) {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, SQLDialect.MYSQL);
            return create.deleteFrom(table(TABLE_NAME))
                    .where(field(FIELD_NAME).eq(playlist).and(field(FIELD_GUILDID).eq(guildId))).execute();
        } catch (SQLException e) {
            Logger.error(e);
        }
        return 0;
    }

    public static Playlist createPlaylist(String name, long guildId) {
        Playlist createdPlaylist = null;
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            create.insertInto(table(TABLE_NAME), field(FIELD_NAME), field(FIELD_GUILDID)).values(name, guildId)
                    .execute();
            createdPlaylist = getPlaylist(name, guildId);
        } catch (SQLException e) {
            Logger.error(e);
        }

        return createdPlaylist;
    }

    public static Playlist getPlaylist(String name, long guildId) {
        Playlist playlist = null;
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Record result = create.select().from(table(TABLE_NAME))
                    .where(field(FIELD_NAME).eq(name).and(field(FIELD_GUILDID).eq(guildId))).fetchOne();
            if (result != null) {
                playlist = new Playlist(result.get(FIELD_NAME, String.class), result.get(FIELD_GUILDID, long.class));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return playlist;
    }
}
