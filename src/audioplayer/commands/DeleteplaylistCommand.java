package audioplayer.commands;

import audioplayer.PlaylistManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import static main.Commands.sendBeautifulMessage;

public class DeleteplaylistCommand extends Command {
    public DeleteplaylistCommand() {
        setCommand(prefix + "deleteplaylist");
        setPermission("Bananenchefs");
        setTopic("music");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 2) {
            String result;
            result = PlaylistManager.deletePlaylist(argStrings[1]) ? "deleted playlist " + argStrings[1] : "playlist " + argStrings[1] + " not found";
            sendBeautifulMessage(event, result);
        }
    }
}
