package metahandler;

public class MetaHandler {
	static final long startNano = System.nanoTime();

	public static String runtime() {
		long nanoNow = System.nanoTime();
		long diff = nanoNow - startNano;

		long mins = diff / 1000000000 / 60;

		long days = (int) mins / 60 / 24;
		long hours = (int) mins / 60 % 24;
		mins = (int) mins % 60;
		return "I am currently running since " + days + " days, " + hours + " hours, " + mins + " minutes. \n\n";
	}

	public static String greet() {
		return "Hello! My name is DirisSlave and I'm here to serve you. \n\n";
	}

	public static String helpMessage() {
		return "You can have a look at all commands by typing #help in the chat. If it is not working, please ask for permissions from the administrator. \n\n";
	}
}
