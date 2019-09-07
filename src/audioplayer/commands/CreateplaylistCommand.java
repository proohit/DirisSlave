package audioplayer.commands;

import audioplayer.PlaylistManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class CreateplaylistCommand extends Command {
    public CreateplaylistCommand() {
        setCommand(prefix+"createplaylist");
        setPermission("Bananenchefs");
        setTopic("music");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if(argStrings.length == 2) {
            if(PlaylistManager.createPlaylist(argStrings[1])) {
                main.Commands.sendBeautifulMessage(event, "playlist " + argStrings[1] + " created.");
            } else {
                main.Commands.sendBeautifulMessage(event, "this playlist has already been created before");
            }
        }
    }
}
