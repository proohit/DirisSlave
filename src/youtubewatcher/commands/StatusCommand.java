package youtubewatcher.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import youtubewatcher.YoutubeWatcher;

public class StatusCommand extends Command {
    public StatusCommand() {
        setCommand(prefix + "ytstatus");
        setPermission("everyone");
        setTopic("ytwatcher");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings[1].equals("status")) {
            Commands.sendMessage(event.getTextChannel(), YoutubeWatcher.status());
        }
    }
}
