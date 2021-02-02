package audioplayer.commands.playlist;

import database.Playlist;
import database.PlaylistTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class CreatePlaylistCommand extends Command {

    public CreatePlaylistCommand() {
        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 1) {
            Playlist createdPlaylist = PlaylistTable.createPlaylist(argStrings[0]);
            if (createdPlaylist != null) {
                main.Commands.sendBeautifulMessage(event, "playlist " + argStrings[2] + " created.");
            } else {
                main.Commands.sendBeautifulMessage(event, "this playlist has already been created before");
            }
        }
    }

    @Override
    protected String[] defineCommand() {
        return new String[] { "create", "new" };
    }

    @Override
    protected String defineDescription() {
        return "creates a new empty playlist with the given name";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "<playlist name>";
    }
}