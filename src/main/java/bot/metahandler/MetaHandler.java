package bot.metahandler;

import bot.main.CommandManager;
import bot.main.ReadPropertyFile;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MetaHandler {
	private MetaHandler() {
	}

	static final long START_TIME = System.nanoTime();

	public static String getMetaInformation(MessageReceivedEvent event) {
		return String.format("%s %n%n %s %n %s %n%n %s %n%n", greet(), runtime(), version(), helpMessage(event));
	}

	private static String runtime() {
		long now = System.nanoTime();
		long diff = now - START_TIME;

		long mins = diff / 1000000000 / 60;

		long days = (int) mins / 60 / 24;
		long hours = (int) mins / 60 % 24;
		mins = (int) mins % 60;
		return "I am currently running since " + days + " days, " + hours + " hours, " + mins + " minutes.";
	}

	private static String version() {
		String version = ReadPropertyFile.getInstance().getVersion();
		return String.format("I am currently running version %s.", version);
	}

	private static String greet() {
		return "Hello! My name is DirisSlave and I'm here to serve you.";
	}

	private static String helpMessage(MessageReceivedEvent event) {
		Long guildId = event.getGuild().getIdLong();
		String prefix = CommandManager.CONFIGURATION_MANAGER.getPrefixForGuild(guildId);
		return String.format(
				"You can have a look at all commands by typing %shelp in the chat. If it is not working, please ask for permissions from the administrator.",
				prefix);
	}
}
