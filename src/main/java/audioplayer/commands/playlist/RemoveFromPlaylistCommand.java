package audioplayer.commands.playlist;

import static main.Commands.sendBeautifulMessage;

import database.Song;
import database.SongPlaylistTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class RemoveFromPlaylistCommand extends Command {

    public RemoveFromPlaylistCommand() {
        addPermission("everyone");
        addCommendPrefix("removefrom", "rmfrom");
        setDescription("removes the given index from the playlist");
        setTopic("music");
        setHelpString("<playlist name> <index of song in playlist. type playlist list playlistname to get indexes>");
        setMinArguments(2);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String playlistToRemoveFrom = argStrings[0];
        int indexOfSongToRemove = Integer.parseInt(argStrings[1]) - 1;
        Song song = SongPlaylistTable.getPlaylistByName(playlistToRemoveFrom).getSongs().get(indexOfSongToRemove);
        if (song != null) {
            if (SongPlaylistTable.removeSongFromPlaylist(playlistToRemoveFrom, song) != 0) {
                sendBeautifulMessage(event, "deleted " + song.getTitle() + " from " + playlistToRemoveFrom);
            }
        } else {
            sendBeautifulMessage(event, "playlist or song not found.");
        }
    }

}
