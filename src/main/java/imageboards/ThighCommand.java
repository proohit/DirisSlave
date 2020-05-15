package imageboards;

import api.NekobotHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class ThighCommand extends Command {
    public ThighCommand() {
        setCommand(prefix + "thigh");
        addPermission("Bananenchefs");
        setTopic("images");
        setDescription("Stockings, socks and pantys. Everything you desire");
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        NekobotHandler nekobotHandler = new NekobotHandler();
        String imageUrl = nekobotHandler.getThighImage();
        Commands.sendMessage(event, imageUrl);
    }
}
