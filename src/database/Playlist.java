package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Playlist {


    private String name;
    private ArrayList<Song> songs = new ArrayList<>();

    public Playlist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSong(Song song) {
        if(song != null) songs.add(song);
    }
    public List<Song> getSongs() {
        return songs;
    }
    public boolean isEmpty() {
        return songs.isEmpty();
    }
}
