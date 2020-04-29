package imageboards;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.UrlHandler;

public class ThighCommand extends Command {
    public ThighCommand() {
        setCommand(prefix+"thigh");
        setPermission("Bananenchefs");
        setTopic("images");
        setDescription("Stockings, socks and pantys. Everything you desire");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        Commands.sendMessage(event, UrlHandler.getImage("https://nekobot.xyz/api/image?type=thigh"));

    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand()+"***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }
}