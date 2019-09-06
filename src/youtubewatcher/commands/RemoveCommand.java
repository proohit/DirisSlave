package youtubewatcher.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import youtubewatcher.YoutubeWatcher;

public class RemoveCommand extends Command {
    public RemoveCommand() {
        setCommand(prefix+"ytremove");
        setPermission("everyone");
        setTopic("ytwatcher");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        try {
            String channelname = "";
            for (int i = 2; i < argStrings.length; i++) {
                channelname += argStrings[i];
                if (i < argStrings.length - 1)
                    channelname += " ";
            }
            YoutubeWatcher.remove(channelname);
            Commands.sendMessage(event.getTextChannel(), "Not watching " + channelname + " anymore.");
        } catch (Exception e) {
            Commands.sendMessage(event.getTextChannel(),
                    "Sorry, that didn't work! To remove a channel from being watched, please type: " + getCommand() + " <channelName>");
        }
    }
}
