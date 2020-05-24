package audioplayer.commands.playlist;

import database.PlaylistTable;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class DeletePlaylistCommand extends Command {
    public DeletePlaylistCommand() {
        this.setCommand("delete");
        this.setDescription("Deletes the playlist with the given name");
        this.setHelpString("<playlist name>\n");
        this.setTopic("music");

        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            String result;
            result = PlaylistTable.deletePlaylist(argStrings[0]) == 1 ? "deleted playlist " + argStrings[0]
                    : "playlist " + argStrings[0] + " not found";
            Commands.sendBeautifulMessage(event, result);
        }
    }

}
