package audioplayer.commands.playlist;

import database.Playlist;
import database.PlaylistTable;
import main.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class CreatePlaylistCommand extends Command {

    public CreatePlaylistCommand() {
        addPermission("everyone");
        addCommendPrefix("create", "new");
        setDescription("creates a new empty playlist with the given name");
        setTopic("music");
        setHelpString("<playlist name>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String playlistName = argStrings[0];
        if (PlaylistTable.getPlaylist(playlistName) != null) {
            CommandManager.sendBeautifulMessage(event, "this playlist has already been created before");
        }
        Playlist createdPlaylist = PlaylistTable.createPlaylist(playlistName);
        if (createdPlaylist != null) {
            CommandManager.sendBeautifulMessage(event, "playlist " + createdPlaylist.getName() + " created.");
        }
    }

}