package youtubewatcher.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import youtubewatcher.YoutubeWatcher;

public class YtWatcherStatusCommand extends Command {
    public YtWatcherStatusCommand() {
        addPermission("everyone");
    }

    @Override
    protected String defineCommand() {
        return "status";
    }

    @Override
    protected String defineDescription() {
        return "lists all currently watched channels";
    }

    @Override
    protected String defineTopic() {
        return "ytwatcher";
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.sendMessage(event, YoutubeWatcher.status());
    }

    @Override
    protected String defineHelpString() {
        return "";
    }

}