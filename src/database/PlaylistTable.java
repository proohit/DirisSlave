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
        Connection con = DBManager.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM playlist");
            while(rs.next()) {
                playlists.add(new Playlist(rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }
}
