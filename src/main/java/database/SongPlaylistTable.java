package database;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.foreignKey;
import static org.jooq.impl.DSL.primaryKey;
import static org.jooq.impl.DSL.table;
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

public class SongPlaylistTable {

    private SongPlaylistTable() {
    }

    public static final String TABLE_NAME = "playlist_songs";
    private static final String FIELD_SONGPLAYLISTID = "songplaylistid";
    private static final String FIELD_SONGID = "songid";
    private static final String FIELD_PLAYLISTNAME = "playlistname";

    public static void createTable() {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            try (CreateTableColumnStep table = create.createTable(TABLE_NAME)) {
                table.column(FIELD_SONGPLAYLISTID, INTEGER.identity(true)).column(FIELD_SONGID, INTEGER)
                        .column(FIELD_PLAYLISTNAME, VARCHAR(255))
                        .constraints(primaryKey(FIELD_SONGPLAYLISTID),
                                foreignKey(FIELD_SONGID).references(SongTable.TABLE_NAME, SongTable.FIELD_ID),
                                foreignKey(FIELD_PLAYLISTNAME).references(PlaylistTable.TABLE_NAME,
                                        PlaylistTable.FIELD_NAME))
                        .execute();
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
    }

    public static List<Song> getSongsByPlaylist(String playlist) {
        List<Song> songs = new ArrayList<>();
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Result<Record> result = create.select().from(table(TABLE_NAME))
                    .where(field(FIELD_PLAYLISTNAME).eq(playlist)).fetch();
            for (Record record : result) {
                songs.add(SongTable.getSongById(record.get(FIELD_SONGID, Integer.class)));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return songs;
    }

    public static Playlist getPlaylistByName(String name) {
        Playlist playlist = new Playlist(name);
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Result<Record> result = create.select().from(table(TABLE_NAME)).where(field(FIELD_PLAYLISTNAME).eq(name))
                    .fetch();
            for (Record record : result) {
                playlist.addSong(SongTable.getSongById(record.get(FIELD_SONGID, Integer.class)));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return playlist;
    }

    public static int insertSongIntoPlaylist(Song song, Playlist playlist) {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            return create.insertInto(table(TABLE_NAME), field(FIELD_SONGID), field(FIELD_PLAYLISTNAME))
                    .values(song.getId(), playlist.getName()).execute();
        } catch (SQLException e) {
            Logger.error(e);
        }
        return 0;
    }

    public static int removeSongFromPlaylist(String playlist, Song song) {

        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            return create.deleteFrom(table(TABLE_NAME))
                    .where(field(FIELD_SONGID).eq(song.getId()).and(field(FIELD_PLAYLISTNAME).eq(playlist))).execute();
        } catch (SQLException e) {
            Logger.error(e);
        }
        return 0;
    }

}
