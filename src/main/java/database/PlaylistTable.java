package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistTable {

    public static List<Playlist> getPlaylists() {
        ArrayList<Playlist> playlists = new ArrayList<>();

        Connection con = DBManager.connect();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM playlist");
            while (rs.next()) {
                playlists.add(new Playlist(rs.getString("name")));
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
