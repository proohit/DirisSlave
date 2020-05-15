package imageboards;

import api.NekobotHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.UrlHandler;

public class GahCommand extends Command {
    public GahCommand() {
        setCommand(prefix + "gah");
        addPermission("everyone");
        setTopic("images");
        setDescription("JUST. DO. IT.");
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
        String imageUrl = nekobotHandler.getGahImage();
        Commands.sendMessage(event, imageUrl);
    }
}
