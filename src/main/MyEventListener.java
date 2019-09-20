package main;

import database.DBManager;
import database.Song;
import database.SongPlaylistTable;
import database.SongTable;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import weatherservice.WeatherWatcher;

public class MyEventListener extends ListenerAdapter {
	Commands commander;
	public MyEventListener(JDA jda) {
//		commander = new Commands();
//		youtubewatcher.YoutubeWatcher.start(jda);
//		WeatherWatcher.start(jda);
		DBManager.connect();
		SongPlaylistTable.getSongsByPlaylist("TestPlaylist").stream().forEach(song -> System.out.println(song.getTitle()));
		Song song = new Song("testSong des neue","https://www.youtube.com/watch?v=1adgadg35");
		SongTable.insertSong(song);
		SongTable.getAllSongs().stream().forEach(lied -> System.out.println(lied.getTitle()));
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		commander.handle(event);
	}
}
