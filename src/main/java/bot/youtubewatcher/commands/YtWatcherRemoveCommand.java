package bot.youtubewatcher.commands;

import bot.main.MessageUtils;
import bot.shared.commands.Command;
import bot.youtubewatcher.YoutubeWatcher;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class YtWatcherRemoveCommand extends Command {

    public YtWatcherRemoveCommand() {
        addPermission("everyone");
        addCommendPrefix("remove", "rm");
        setDescription("removes a channel to not notify for new videos");
        setTopic("ytwatcher");
        setHelpString("<name of channel, type .yt status to see current channels>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        try {
            String channelname = "";
            for (int i = 1; i < argStrings.length; i++) {
                channelname += argStrings[i];
                if (i < argStrings.length - 1)
                    channelname += " ";
            }
            YoutubeWatcher.remove(channelname);
            MessageUtils.sendMessage(event, "Not watching " + channelname + " anymore.");
        } catch (Exception e) {
            MessageUtils.sendMessage(event, getHelp());
        }
    }

}