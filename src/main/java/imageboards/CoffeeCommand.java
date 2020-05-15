package imageboards;

import api.NekobotHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

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
        NekobotHandler nekobotHandler = new NekobotHandler();
        String imageUrl = nekobotHandler.getCoffeImage();
        Commands.sendMessage(event, imageUrl);
    }
}
