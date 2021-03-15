package util.commands;

import java.util.List;

import main.CommandManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class SetMusicChannelCommand extends Command {
    public SetMusicChannelCommand() {
        addPermission("Bananenchefs");
        addCommendPrefix("musicchannel");
        setTopic("util");
        setDescription("Changes the music channel");
        setHelpString("<channel name or \"everywhere\" to reset>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String channelName = String.join(" ", argStrings);
        long guildId = event.getGuild().getIdLong();

        if ("everywhere".equals(channelName)) {
            CommandManager.CONFIGURATION_MANAGER.setMusicchannelForGuild(guildId, channelName);
            CommandManager.sendBeautifulMessage(event, String
                    .format("All channels can be used as music channel on server %s", event.getGuild().getName()));
        } else {
            List<TextChannel> channelsOfGuildByName = event.getGuild().getTextChannelsByName(channelName, true);
            if (channelsOfGuildByName.isEmpty()) {
                CommandManager.sendBeautifulMessage(event, String.format("Channel %s not found", channelName));
                return;
            }
            CommandManager.CONFIGURATION_MANAGER.setMusicchannelForGuild(guildId,
                    channelsOfGuildByName.get(0).getName());
            CommandManager.sendBeautifulMessage(event, String.format("Set channel %s as music channel for server %s",
                    channelsOfGuildByName.get(0).getName(), event.getGuild().getName()));
        }

    }
}
