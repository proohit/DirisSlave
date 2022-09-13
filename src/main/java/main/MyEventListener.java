package main;

import java.util.Arrays;
import java.util.List;

import org.tinylog.Logger;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import util.commands.HelpCommand;
import weather.WeatherWatcher;

public class MyEventListener extends ListenerAdapter {
    Commands commander;
    CommandManager commandManager;

    public MyEventListener(JDA jda) {
        commandManager = new CommandManager();
        WeatherWatcher.start(jda);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            if (event.getAuthor().isBot())
                return;
            commandManager.handle(event);
        } catch (Exception e) {
            Logger.error(e);
            List<TextChannel> channels = event.getGuild().getTextChannelsByName("debugchannel", true);
            if (!channels.isEmpty()) {
                MessageUtils.sendMessage(channels.get(0), String.format("%s%s", e.getMessage(),
                        Arrays.toString(Arrays.copyOfRange(e.getStackTrace(), 0, 5))));
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        commandManager.handle(event);
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (!event.getUser().isBot()) {
            HelpCommand.getInstance().handlePageRequest(event);
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        HelpCommand helpCommand = HelpCommand.getInstance();
        Message lastSentHelpMessage = helpCommand.getLastSentHelpMessage();
        if (lastSentHelpMessage == null) {
            return;
        }
        if (event.getMessageId().equals(lastSentHelpMessage.getId())) {
            lastSentHelpMessage.addReaction(event.getReaction().getEmoji()).queue();
            helpCommand.handlePageRequest(event);
        }
    }
}
