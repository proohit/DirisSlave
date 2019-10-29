package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SongHistoryTable {

    public static Map<Integer,Song> getLastSongs() {
        Map<Integer, Song> songs = new HashMap<>();

        try {
            Connection con = DBManager.connected()?DBManager.getConnection():DBManager.connect();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Song_history order by timestamp desc LIMIT 10");
            while (rs.next()) {
                Song song = SongTable.getSongById(rs.getInt("songId"));
                song.setTimestamp(rs.getTimestamp("timestamp").toString());
                songs.put(rs.getInt("historyId"),song);
            }
        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();
        }
        return songs;
    }

    public static void insertHistoryItem(Song song) {
        try {
            Connection con = DBManager.connected()?DBManager.getConnection():DBManager.connect();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO Song_history(songId) VALUES (" + song.getId() + ")");

        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();        }
    }

    public static boolean hasEntry(Song song) {
        boolean hasEntry = false;
        try {
            Connection con = DBManager.connected()?DBManager.getConnection():DBManager.connect();

            Statement stmt = con.createStatement();
            if(stmt.executeQuery("SELECT * FROM Song_history where songId=" + song.getId()).next()) hasEntry = true;

        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();        }
        return hasEntry;
    }
    public static void updateTimestamp(Song song) {
        try {
            Connection con = DBManager.connected()?DBManager.getConnection():DBManager.connect();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE Song_history set timestamp = CURRENT_TIMESTAMP WHERE songId=" + song.getId());
        } catch (SQLException e) {
            if(!DBManager.connected()) DBManager.connect();
            e.printStackTrace();        }
    }
}
