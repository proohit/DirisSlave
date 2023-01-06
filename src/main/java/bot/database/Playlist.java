package bot.database;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private long guildId;
    private String name;
    private ArrayList<Song> songs = new ArrayList<>();

    public Playlist(String name, long guildId) {
        this.name = name;
        this.guildId = guildId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSong(Song song) {
        if (song != null)
            songs.add(song);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public boolean isEmpty() {
        return songs.isEmpty();
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }
}
