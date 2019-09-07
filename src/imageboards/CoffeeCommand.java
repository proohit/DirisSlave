package imageboards;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class CoffeeCommand extends Command {
    public CoffeeCommand() {
        setCommand(prefix+"coffee");
        setPermission("everyone");
        setTopic("images");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        Commands.sendMessage(event, UrlHandler.getImage("https://nekobot.xyz/api/image?type=coffee"));

    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand()+"***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }
}
