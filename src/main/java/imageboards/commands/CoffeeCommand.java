package imageboards.commands;

import imageboards.api.NekobotHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class CoffeeCommand extends Command {
    public CoffeeCommand() {
        addPermission("everyone");
        addCommendPrefix("coffee");
        setDescription("Enjoy your coffee with a gif");
        setTopic("images");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        NekobotHandler nekobotHandler = new NekobotHandler();
        String imageUrl = nekobotHandler.getCoffeImage();
        Commands.sendMessage(event, imageUrl);
    }

}
