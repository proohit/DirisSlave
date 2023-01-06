package bot.youtubewatcher.commands;

import bot.main.MessageUtils;
import bot.shared.commands.Command;
import bot.youtubewatcher.YoutubeWatcher;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class YtWatcherStatusCommand extends Command {
    public YtWatcherStatusCommand() {
        addPermission("everyone");
        addCommendPrefix("status");
        setDescription("lists all currently watched channels");
        setTopic("ytwatcher");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        MessageUtils.sendMessage(event, YoutubeWatcher.status());
    }

}