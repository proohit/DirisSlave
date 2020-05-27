package youtubewatcher.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import youtubewatcher.YoutubeWatcher;
import youtubewatcher.YoutubeXML;

public class YtWatcherCommand extends Command {
    public YtWatcherCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {

        if (argStrings.length <= 0) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        if (argStrings[0].equals("add")) {
            for (YoutubeXML urls : YoutubeWatcher.latestvideos.keySet()) {
                if (argStrings[1].equals(urls.getUrl())) {
                    Commands.sendMessage(event, "this channel was already added");
                    return;
                }
            }
            YoutubeWatcher.update(argStrings[1]);
            for (YoutubeXML yt : YoutubeWatcher.latestvideos.keySet()) {
                if (yt.getUrl().equals(argStrings[1])) {
                    Commands.sendMessage(event, "added channel to watch: " + yt.getChannelName());
                    return;
                }
            }
        } else if (argStrings[0].equals("remove")) {
            try {
                String channelname = "";
                for (int i = 1; i < argStrings.length; i++) {
                    channelname += argStrings[i];
                    if (i < argStrings.length - 1)
                        channelname += " ";
                }
                YoutubeWatcher.remove(channelname);
                Commands.sendMessage(event, "Not watching " + channelname + " anymore.");
            } catch (Exception e) {
                Commands.sendMessage(event,
                        "Sorry, that didn't work! To remove a channel from being watched, please type: " + getCommand()
                                + " <channelName>");
            }
        } else if (argStrings[0].equals("status")) {
            Commands.sendMessage(event, YoutubeWatcher.status());
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
        return "add <url to youtube channel>\nstatus\nremove <name of channel, type .yt status to see current channels> ";
    }
}
