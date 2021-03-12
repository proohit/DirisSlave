package imageboards.commands;

import imageboards.api.NekobotHandler;
import main.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

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
        CommandManager.sendMessage(event, imageUrl);
    }

}
