package imageboards;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class GahCommand extends Command {
    public GahCommand() {
        setCommand(prefix + "gah");
        setPermission("everyone");
        setTopic("images");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        main.Commands.sendMessage(event, UrlHandler.getImage("https://nekobot.xyz/api/image?type=gah"));

    }
}
