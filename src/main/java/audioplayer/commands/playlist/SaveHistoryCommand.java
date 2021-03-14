package audioplayer.commands.playlist;

import static main.CommandManager.sendBeautifulMessage;

import database.Playlist;
import database.PlaylistTable;
import database.SongHistoryTable;
import database.SongPlaylistTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

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
            sendBeautifulMessage(event, "playlistname already exists");
        } else {
            PlaylistTable.createPlaylist(playlistName, guildId);
            final Playlist playlist = PlaylistTable.getPlaylist(playlistName, guildId);
            SongHistoryTable.getLastSongs().forEach((id, song) -> {
                SongPlaylistTable.insertSongIntoPlaylist(song, playlist);
            });
            sendBeautifulMessage(event, "saved playlist " + playlistName);
        }
    }

}
