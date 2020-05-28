package imageboards.commands;

import imageboards.api.NekobotHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class CoffeeCommand extends Command {
    public CoffeeCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        NekobotHandler nekobotHandler = new NekobotHandler();
        String imageUrl = nekobotHandler.getCoffeImage();
        Commands.sendMessage(event, imageUrl);
    }

    @Override
    protected String defineCommand() {
        return prefix + "coffee";
    }

    @Override
    protected String defineDescription() {
        return "Enjoy your coffee with a gif";
    }

    @Override
    protected String defineTopic() {
        return "images";
    }

    @Override
    protected String defineHelpString() {
        return "";
    }
}
