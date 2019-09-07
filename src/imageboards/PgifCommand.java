package imageboards;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class PgifCommand extends Command {
    public PgifCommand() {
        setCommand(prefix+"pgif");
        setPermission("Bananenchefs");
        setTopic("images");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        main.Commands.sendMessage(event, UrlHandler.getImage("https://nekobot.xyz/api/image?type=pgif"));

    }
}
