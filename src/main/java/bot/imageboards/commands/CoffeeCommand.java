package bot.imageboards.commands;

import bot.imageboards.api.NekobotHandler;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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
