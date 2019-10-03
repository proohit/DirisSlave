package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongTable {

    public static Song getSongById(int id) {

        try {
            Connection con = DBManager.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM songs WHERE id =" + id + ";");
            if (rs.next()) return new Song(rs.getInt("id"), rs.getString("title"), rs.getString("url"));
        } catch (SQLException e) {

        }
        return null;
    }

    public static List<Song> getAllSongs() {
        ArrayList<Song> result = new ArrayList<>();
        try {
            Connection con = DBManager.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM songs;");
            while (rs.next()) {
                result.add(new Song(rs.getInt("id"), rs.getString("title"), rs.getString("url")));
            }
            return result;
        } catch (SQLException e) {

        }
        return null;
    }

    public static void insertSong(Song song) {
        try {
            Connection con = DBManager.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO songs(title,url) VALUES('" + song.getTitle() + "','" + song.getUrl() + "');", Statement.RETURN_GENERATED_KEYS);
            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) song.setId(key.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Song> getSongsByTitle(String title) {
        ArrayList<Song> result = new ArrayList<>();
        try {
            Connection con = DBManager.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM songs WHERE title=\'" + title + "\'");
            while (rs.next()) {
                result.add(new Song(rs.getInt("id"), rs.getString("title"), rs.getString("url")));
            }
        } catch (SQLException e) {

        }
        return result;
    }
}
