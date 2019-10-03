package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongPlaylistTable {

    public static List<Song> getSongsByPlaylist(String playlist) {
        ArrayList<Song> result = new ArrayList<>();
        try {
            Connection con = DBManager.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM playlist_songs WHERE playlistname ='" + playlist + "';");
            while (rs.next()) {
                result.add(SongTable.getSongById(rs.getInt("songid")));
            }
        } catch (SQLException e) {

        }
        return result;
    }

    public static int insertSongIntoPlaylist(Song song, Playlist playlist) {
        try {
            Connection con = DBManager.getConnection();
            Statement stmt = con.createStatement();
            return stmt.executeUpdate("INSERT INTO playlist_songs(songid, playlistname) VALUES(" + song.getId() + ",'" + playlist.getName() + "');");
        } catch (SQLException e) {

        }
        return 0;
    }
}
