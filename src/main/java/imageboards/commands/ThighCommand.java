package imageboards.commands;

import imageboards.api.NekobotHandler;
import main.MessageUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class ThighCommand extends Command {
    public ThighCommand() {
        addPermission("Bananenchefs");
        addCommendPrefix("thigh");
        setDescription("Stockings, socks and pantys. Everything you desire");
        setTopic("images");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        NekobotHandler nekobotHandler = new NekobotHandler();
        String imageUrl = nekobotHandler.getThighImage();
        MessageUtils.sendMessage(event, imageUrl);
    }

}
