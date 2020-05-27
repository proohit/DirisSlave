package imageboards;

import api.NekobotHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

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
    protected String defineCommand() {
        return prefix + "gah";
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
