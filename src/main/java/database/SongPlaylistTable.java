package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jooq.CreateTableColumnStep;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.tinylog.Logger;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

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
        ArrayList<Song> result = new ArrayList<>();
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM playlist_songs WHERE playlistname ='" + playlist + "';");
            while (rs.next()) {
                result.add(SongTable.getSongById(rs.getInt("songid")));
            }
        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return result;
    }
    public static Playlist getPlaylistByName(String name) {
        Playlist playlist = new Playlist(name);
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM playlist_songs WHERE playlistname ='" + name + "';");
            while(rs.next()) {
                playlist.addSong(SongTable.getSongById(rs.getInt("songid")));
            }
        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return playlist;
    }
    public static int insertSongIntoPlaylist(Song song, Playlist playlist) {
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            return stmt.executeUpdate("INSERT INTO playlist_songs(songid, playlistname) VALUES(" + song.getId() + ",'" + playlist.getName() + "');");
        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return 0;
    }

    public static int removeSongFromPlaylist(String playlist, Song song) {
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            return stmt.executeUpdate("DELETE FROM playlist_songs WHERE songid=" + song.getId());
        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();        }
        return 0;
    }

    public static Song getSongOfPlaylistByIndex(int index) {
        Song song = null;
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM playlist_songs WHERE songplaylistid=" + index);
            if(rs.next()) song = SongTable.getSongById(rs.getInt("songid"));
        } catch(SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();        }
        return song;
    }
}
