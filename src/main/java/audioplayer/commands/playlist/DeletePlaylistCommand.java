package audioplayer.commands.playlist;

import database.PlaylistTable;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class DeletePlaylistCommand extends Command {
    public DeletePlaylistCommand() {
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

    @Override
    protected String defineCommand() {
        return "delete";
    }

    @Override
    protected String defineDescription() {
        return "Deletes the playlist with the given name";
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
