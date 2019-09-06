package audioplayer.commands;

import audioplayer.PlaylistManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import static main.Commands.sendBeautifulMessage;

public class SavehistoryCommand extends Command {
    public SavehistoryCommand() {
        setCommand(prefix + "savehistory");
        setPermission("Bananenchefs");
        setTopic("music");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 2) {
            PlaylistManager.savePlaylistFromHistory(argStrings[1]);
            sendBeautifulMessage(event, "saved playlist " + argStrings[1]);
        }
    }
}
