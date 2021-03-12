package audioplayer.commands.playlist;

import database.Playlist;
import database.PlaylistTable;
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
        Playlist createdPlaylist = PlaylistTable.createPlaylist(argStrings[0]);
        if (createdPlaylist != null) {
            main.CommandManager.sendBeautifulMessage(event, "playlist " + argStrings[2] + " created.");
        } else {
            main.CommandManager.sendBeautifulMessage(event, "this playlist has already been created before");
        }
    }

}