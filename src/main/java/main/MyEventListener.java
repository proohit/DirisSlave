package main;

import javax.annotation.Nonnull;

import database.DBManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import util.commands.HelpCommand;
import weather.WeatherWatcher;

public class MyEventListener extends ListenerAdapter {
    Commands commander;

    public MyEventListener(JDA jda) {
        commander = new Commands();
        youtubewatcher.YoutubeWatcher.start(jda);
        WeatherWatcher.start(jda);
        DBManager.connect();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
        commander.handle(event);
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getUser().isBot()) {
            return;
        } else {
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
            lastSentHelpMessage.addReaction(event.getReactionEmote().getEmoji()).queue();
            helpCommand.handlePageRequest(event);
        }
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        super.onReady(event);
        Startup.jda.getTextChannelById("544815528179531776").sendMessage("starting the engine...").queue();
    }
}
