package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.tinylog.Logger;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class PermissionManager {
    List<Command> commands;
    private static Map<Long, String> musicChannels = new HashMap<>();

    PermissionManager(List<Command> commands) {
        this.commands = commands;
        List<Guild> botGuilds = Startup.jda.getGuilds();
        botGuilds.forEach(guild -> musicChannels.put(guild.getIdLong(), "everywhere"));
        Logger.info("Bot logged in at servers: {}", botGuilds);
    }

    public boolean isPermitted(Member member, Command insertedCommand) {
        if (insertedCommand.getPermissions().contains("everyone"))
            return true;
        for (Role role : member.getRoles()) {
            if (insertedCommand.getPermissions().contains(role.getName()))
                return true;
        }
        return false;
    }

    public List<Command> getPermittedCommands(Member member) {
        return commands.stream().filter(command -> isPermitted(member, command))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public String getRegisteredMusicChannelByGuildId(long guildId) {
        return musicChannels.get(guildId);
    }

    public void setRegisteredMusicChannelByGuildId(long guildId, String channelName) {
        musicChannels.put(guildId, channelName);
    }

    public boolean isMusicChannelAppropriate(MessageReceivedEvent event) {
        long associatedGuildId = event.getGuild().getIdLong();
        String associatedChannelName = event.getTextChannel().getName();
        String registeredMusicChannel = musicChannels.get(associatedGuildId);
        if ("everywhere".equals(registeredMusicChannel) || associatedChannelName.equals(registeredMusicChannel)) {
            return true;
        } else {
            return false;
        }
    }
}
