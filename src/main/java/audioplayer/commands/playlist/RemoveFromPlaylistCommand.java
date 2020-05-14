package audioplayer.commands.playlist;

import database.Song;
import database.SongPlaylistTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import static main.Commands.sendBeautifulMessage;

public class RemoveFromPlaylistCommand extends Command {

    public RemoveFromPlaylistCommand() {
        this.setCommand("remove");
        this.setDescription("");
        this.setHelpString("");
        this.setTopic("music");

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

}
