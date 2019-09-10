package main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import weatherservice.WeatherWatcher;

public class MyEventListener extends ListenerAdapter {
	Commands commander;
	public MyEventListener(JDA jda) {
		commander = new Commands();
		youtubewatcher.YoutubeWatcher.start(jda);
		WeatherWatcher.start(jda);

	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		commander.handle(event);
	}
}
