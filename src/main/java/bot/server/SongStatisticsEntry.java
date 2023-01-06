package bot.server;

import bot.database.Song;

public class SongStatisticsEntry {
    Song song;
    int numberOfPlays;

    public SongStatisticsEntry(Song song, int numberOfPlays) {
        this.song = song;
        this.numberOfPlays = numberOfPlays;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public int getNumberOfPlays() {
        return numberOfPlays;
    }

    public void setNumberOfPlays(int numberOfPlays) {
        this.numberOfPlays = numberOfPlays;
    }

}
