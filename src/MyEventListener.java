import audioplayer.AudioPlayer;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MyEventListener extends ListenerAdapter {
	Commands commander;
	public MyEventListener(JDA jda) {
		commander = new Commands();
//		YoutubeWatcher.start(jda);
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		commander.handle(event);
	}
}
