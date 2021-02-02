package youtubewatcher.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;
import youtubewatcher.YoutubeWatcher;

public class YtWatcherRemoveCommand extends Command {

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 1) {
            Commands.sendMessage(event, getHelp());
        } else {
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
                Commands.sendMessage(event, getHelp());
            }
        }
    }

    public YtWatcherRemoveCommand() {
        addPermission("everyone");
        addCommendPrefix("remove", "rm");
        setDescription("removes a channel to not notify for new videos");
        setTopic("ytwatcher");
        setHelpString("<name of channel, type .yt status to see current channels>");
    }
}