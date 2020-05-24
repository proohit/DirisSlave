package audioplayer.commands.playlist;

import static main.Commands.sendBeautifulMessage;

import database.Playlist;
import database.PlaylistTable;
import database.SongHistoryTable;
import database.SongPlaylistTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class SaveHistoryCommand extends Command {
    public SaveHistoryCommand() {
        this.setCommand("savehistory");
        this.setDescription("Creates a new playlist with the given name and adds the last 10 songs from history to it");
        this.setHelpString("<playlist name>\n");
        this.setTopic("music");

        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            String playlistName = argStrings[0];
            if (PlaylistTable.getPlaylist(playlistName) != null) {
                sendBeautifulMessage(event, "playlistname already exists");
                return;
            } else {
                PlaylistTable.createPlaylist(playlistName);
                final Playlist playlist = PlaylistTable.getPlaylist(playlistName);
                SongHistoryTable.getLastSongs().forEach((id, song) -> {
                    SongPlaylistTable.insertSongIntoPlaylist(song, playlist);
                });
                sendBeautifulMessage(event, "saved playlist " + playlistName);

            }
        }
    }

}
