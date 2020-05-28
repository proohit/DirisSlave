package youtubewatcher.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;
import youtubewatcher.YoutubeWatcher;
import youtubewatcher.YoutubeXML;

public class YtWatcherAddCommand extends Command {

    @Override
    protected String defineCommand() {
        return "add";
    }

    @Override
    protected String defineDescription() {
        return "adds a youtube channel to watch for new videos";
    }

    @Override
    protected String defineTopic() {
        return "ytwatcher";
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 1) {
            Commands.sendMessage(event, getHelp());
        } else {
            for (YoutubeXML urls : YoutubeWatcher.latestvideos.keySet()) {
                if (argStrings[0].equals(urls.getUrl())) {
                    Commands.sendMessage(event, "this channel was already added");
                    return;
                }
            }
            YoutubeWatcher.update(argStrings[0]);
            for (YoutubeXML yt : YoutubeWatcher.latestvideos.keySet()) {
                if (yt.getUrl().equals(argStrings[0])) {
                    Commands.sendMessage(event, "added channel to watch: " + yt.getChannelName());
                    return;
                }
            }
        }
    }

    @Override
    protected String defineHelpString() {
        return "<url to youtube channel>";
    }

}