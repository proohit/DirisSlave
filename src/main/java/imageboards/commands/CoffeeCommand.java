package imageboards.commands;

import imageboards.api.NekobotHandler;
import main.MessageUtils;
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
        MessageUtils.sendMessage(event, imageUrl);
    }

}
