package youtubewatcher.commands;

import main.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;
import youtubewatcher.YoutubeWatcher;
import youtubewatcher.YoutubeXML;

public class YtWatcherAddCommand extends Command {

    public YtWatcherAddCommand() {
        addPermission("everyone");
        addCommendPrefix("add");
        setDescription("adds a youtube channel to watch for new videos");
        setTopic("ytwatcher");
        setHelpString("<url to youtube channel>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        for (YoutubeXML urls : YoutubeWatcher.latestvideos.keySet()) {
            if (argStrings[0].equals(urls.getUrl())) {
                CommandManager.sendMessage(event, "this channel was already added");
                return;
            }
        }
        YoutubeWatcher.update(argStrings[0]);
        for (YoutubeXML yt : YoutubeWatcher.latestvideos.keySet()) {
            if (yt.getUrl().equals(argStrings[0])) {
                CommandManager.sendMessage(event, "added channel to watch: " + yt.getChannelName());
                return;
            }
        }
    }

}