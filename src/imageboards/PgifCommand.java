package imageboards;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.UrlHandler;

public class PgifCommand extends Command {
    public PgifCommand() {
        setCommand(prefix+"pgif");
        setPermission("Bananenchefs");
        setTopic("images");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        main.Commands.sendMessage(event, UrlHandler.getImage("https://nekobot.xyz/api/image?type=pgif"));

    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand()+"***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }
}
