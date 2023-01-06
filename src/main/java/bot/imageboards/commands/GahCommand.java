package bot.imageboards.commands;

import bot.imageboards.api.NekobotHandler;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GahCommand extends Command {
    public GahCommand() {
        addPermission("everyone");
        addCommendPrefix("gah");
        setDescription("JUST. DO. IT.");
        setTopic("images");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        NekobotHandler nekobotHandler = new NekobotHandler();
        String imageUrl = nekobotHandler.getGahImage();
        MessageUtils.sendMessage(event, imageUrl);
    }

}
