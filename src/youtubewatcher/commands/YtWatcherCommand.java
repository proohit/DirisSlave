package youtubewatcher.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import youtubewatcher.YoutubeWatcher;
import youtubewatcher.YoutubeXML;

public class YtWatcherCommand extends Command {
    public YtWatcherCommand() {
        setCommand(prefix + "yt");
        setPermission("everyone");
        setTopic("ytwatcher");
        setDescription("get notified by your slave, if your followed youtuber has uploaded a new video");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings[1].equals("add")) {
            for (YoutubeXML urls : YoutubeWatcher.latestvideos.keySet()) {
                if (argStrings[2].equals(urls.getUrl())) {
                    Commands.sendMessage(event, "this channel was already added");
                    return;
                }
            }
            YoutubeWatcher.update(argStrings[2]);
            for (YoutubeXML yt : YoutubeWatcher.latestvideos.keySet()) {
                if (yt.getUrl().equals(argStrings[2])) {
                    Commands.sendMessage(event, "added channel to watch: " + yt.getChannelName());
                    return;
                }
            }
        } else if (argStrings[1].equals("remove")) {
            try {
                String channelname = "";
                for (int i = 2; i < argStrings.length; i++) {
                    channelname += argStrings[i];
                    if (i < argStrings.length - 1)
                        channelname += " ";
                }
                YoutubeWatcher.remove(channelname);
                Commands.sendMessage(event, "Not watching " + channelname + " anymore.");
            } catch (Exception e) {
                Commands.sendMessage(event,
                        "Sorry, that didn't work! To remove a channel from being watched, please type: " + getCommand() + " <channelName>");
            }
        } else if (argStrings[1].equals("status")) {
            Commands.sendMessage(event, YoutubeWatcher.status());
        }
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("add <url to youtube channel> \n");
        help.append("status\n");
        help.append("remove <name of channel, type #yt status to see current channels> \n");

        return help.toString();
    }
}
