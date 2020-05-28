package audioplayer.commands.playlist;

import static main.Commands.sendBeautifulMessage;

import database.Song;
import database.SongPlaylistTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class RemoveFromPlaylistCommand extends Command {

    public RemoveFromPlaylistCommand() {
        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 2) {
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

    @Override
    protected String defineCommand() {
        return "remove";
    }

    @Override
    protected String defineDescription() {
        return "removes the given index from the playlist";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "<playlist name> <index of song in playlist. type playlist list playlistname to get indexes>";
    }

}
