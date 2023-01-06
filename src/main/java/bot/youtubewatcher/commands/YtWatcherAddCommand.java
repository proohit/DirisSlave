package bot.youtubewatcher.commands;

import bot.main.MessageUtils;
import bot.shared.commands.Command;
import bot.youtubewatcher.YoutubeWatcher;
import bot.youtubewatcher.YoutubeXML;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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
                MessageUtils.sendMessage(event, "this channel was already added");
                return;
            }
        }
        YoutubeWatcher.update(argStrings[0]);
        for (YoutubeXML yt : YoutubeWatcher.latestvideos.keySet()) {
            if (yt.getUrl().equals(argStrings[0])) {
                MessageUtils.sendMessage(event, "added channel to watch: " + yt.getChannelName());
                return;
            }
        }
    }

}