package bot.audioplayer.commands.playlist;

import bot.database.Playlist;
import bot.database.PlaylistTable;
import bot.database.SongHistoryTable;
import bot.database.SongPlaylistTable;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SaveHistoryCommand extends Command {
    public SaveHistoryCommand() {
        addPermission("everyone");
        addCommendPrefix("savehistory");
        setDescription("Creates a new playlist with the given name and adds the last 10 songs from history to it");
        setTopic("music");
        setHelpString("<playlist name>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String playlistName = argStrings[0];
        long guildId = event.getGuild().getIdLong();
        if (PlaylistTable.getPlaylist(playlistName, guildId) != null) {
            MessageUtils.sendBeautifulMessage(event, "playlistname already exists");
        } else {
            PlaylistTable.createPlaylist(playlistName, guildId);
            final Playlist playlist = PlaylistTable.getPlaylist(playlistName, guildId);
            SongHistoryTable.getLastSongs(event.getGuild().getIdLong()).forEach((id, song) -> {
                SongPlaylistTable.insertSongIntoPlaylist(song, playlist);
            });
            MessageUtils.sendBeautifulMessage(event, "saved playlist " + playlistName);
        }
    }

}
