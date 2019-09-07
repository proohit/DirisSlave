package imageboards;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class ThighCommand extends Command {
    public ThighCommand() {
        setCommand(prefix+"thigh");
        setPermission("Bananenchefs");
        setTopic("images");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        Commands.sendMessage(event, UrlHandler.getImage("https://nekobot.xyz/api/image?type=thigh"));

    }
}
