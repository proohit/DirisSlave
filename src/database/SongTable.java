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
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM songs WHERE id =" + id + ";");
            if (rs.next()) return new Song(rs.getInt("id"), rs.getString("title"), rs.getString("url"));
        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return null;
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
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return null;
    }

    public static void insertSong(Song song) {
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO songs(title,url) VALUES('" + song.getTitle().replace("'","''") + "','" + song.getUrl() + "')", Statement.RETURN_GENERATED_KEYS);
            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) song.setId(key.getInt(1));
        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();        }
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
            if(!DBManager.connected()) DBManager.connect();
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
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return result;
    }

    public static boolean hasSong(String uri) {
        try {
            Connection con = DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM songs WHERE url=\'" + uri + "\'");
            if(rs.next()) return true;
        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return false;
    }
}
