package youtubewatcher.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class YtWatcherCommand extends Command {
    public YtWatcherCommand() {
        addPermission("everyone");
        addSubCommand(new YtWatcherAddCommand());
        addSubCommand(new YtWatcherRemoveCommand());
        addSubCommand(new YtWatcherStatusCommand());
        addCommendPrefix("yt");
        setDescription("get notified by your slave, if your followed youtuber has uploaded a new video");
        setTopic("ytwatcher");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
    }

}
