package database;

public class Configuration {
    private long guildId;
    private String prefix;
    private String musicChannel;

    public Configuration(long guildId, String prefix, String musicChannel) {
        this.guildId = guildId;
        this.prefix = prefix;
        this.musicChannel = musicChannel;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMusicChannel() {
        return musicChannel;
    }

    public void setMusicChannel(String musicChannel) {
        this.musicChannel = musicChannel;
    }

}
