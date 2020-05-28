package youtubewatcher.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class YtWatcherCommand extends Command {
    public YtWatcherCommand() {
        addPermission("everyone");
        addSubCommand(new YtWatcherAddCommand());
        addSubCommand(new YtWatcherRemoveCommand());
        addSubCommand(new YtWatcherStatusCommand());
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length <= 0) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
    }

    @Override
    protected String defineCommand() {
        return prefix + "yt";
    }

    @Override
    protected String defineDescription() {
        return "get notified by your slave, if your followed youtuber has uploaded a new video";
    }

    @Override
    protected String defineTopic() {
        return "ytwatcher";
    }

    @Override
    protected String defineHelpString() {
        return "";
    }
}
