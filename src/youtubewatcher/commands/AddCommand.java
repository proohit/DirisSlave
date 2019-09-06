package youtubewatcher.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import youtubewatcher.YoutubeWatcher;
import youtubewatcher.YoutubeXML;

public class AddCommand extends Command {
    public AddCommand() {
        setCommand(prefix + "ytadd");
        setPermission("everyone");
        setTopic("ytwatcher");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        for (YoutubeXML urls : YoutubeWatcher.latestvideos.keySet()) {
            if (argStrings[2].equals(urls.getUrl())) {
                Commands.sendMessage(event.getTextChannel(), "this channel was already added");
                return;
            }
        }
        YoutubeWatcher.update(argStrings[2]);
        for (YoutubeXML yt : YoutubeWatcher.latestvideos.keySet()) {
            if (yt.getUrl().equals(argStrings[2])) {
                Commands.sendMessage(event.getTextChannel(), "added channel to watch: " + yt.getChannelName());
                return;
            }
        }
    }
}
