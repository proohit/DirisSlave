package bot.server;

public class MusicStatusEntry {
    private String guildName;
    private String songTitle;
    private String currentSongPosition;

    public MusicStatusEntry(String guildName, String songTitle, String currentSongPosition) {
        this.guildName = guildName;
        this.songTitle = songTitle;
        this.currentSongPosition = currentSongPosition;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getCurrentSongPosition() {
        return currentSongPosition;
    }

    public void setCurrentSongPosition(String currentSongPosition) {
        this.currentSongPosition = currentSongPosition;
    }
}
