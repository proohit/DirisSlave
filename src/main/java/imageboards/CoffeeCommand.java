package imageboards;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.UrlHandler;

public class CoffeeCommand extends Command {
    public CoffeeCommand() {
        setCommand(prefix + "coffee");
        addPermission("everyone");
        setTopic("images");
        setDescription("Enjoy your coffee with a gif");
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.sendMessage(event, UrlHandler.getImage("https://nekobot.xyz/api/image?type=coffee"));
    }
}
