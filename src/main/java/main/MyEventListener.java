package main;

import database.DBManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import weatherservice.WeatherWatcher;

import javax.annotation.Nonnull;

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
    public void onReady(@Nonnull ReadyEvent event) {
        super.onReady(event);
        Startup.jda.getTextChannelById("544815528179531776").sendMessage("starting the engine...").queue();
    }
}
