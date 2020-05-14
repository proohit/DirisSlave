package imageboards;

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
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        main.Commands.sendMessage(event, UrlHandler.getImage("https://nekobot.xyz/api/image?type=gah"));

    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand()+"***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        // TODO Auto-generated method stub

    }
}
