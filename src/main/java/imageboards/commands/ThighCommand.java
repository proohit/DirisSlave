package imageboards.commands;

import imageboards.api.NekobotHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class ThighCommand extends Command {
    public ThighCommand() {
        addPermission("Bananenchefs");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        NekobotHandler nekobotHandler = new NekobotHandler();
        String imageUrl = nekobotHandler.getThighImage();
        Commands.sendMessage(event, imageUrl);
    }

    @Override
    protected String[] defineCommand() {
        return new String[] { "thigh" };
    }

    @Override
    protected String defineDescription() {
        return "Stockings, socks and pantys. Everything you desire";
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
