package imageboards.commands;

import imageboards.api.NekobotHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class GahCommand extends Command {
    public GahCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        NekobotHandler nekobotHandler = new NekobotHandler();
        String imageUrl = nekobotHandler.getGahImage();
        Commands.sendMessage(event, imageUrl);
    }

    @Override
    protected String[] defineCommand() {
        return new String[] { "gah" };
    }

    @Override
    protected String defineDescription() {
        return "JUST. DO. IT.";
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
