package youtubewatcher.commands;

import main.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;
import youtubewatcher.YoutubeWatcher;

public class YtWatcherStatusCommand extends Command {
    public YtWatcherStatusCommand() {
        addPermission("everyone");
        addCommendPrefix("status");
        setDescription("lists all currently watched channels");
        setTopic("ytwatcher");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        CommandManager.sendMessage(event, YoutubeWatcher.status());
    }

}