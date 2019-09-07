package imageboards;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class HentaiCommand extends Command {
    public HentaiCommand() {
        setCommand(prefix+"hentai");
        setPermission("Bananenchefs");
        setTopic("images");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        main.Commands.sendMessage(event, UrlHandler.getImage("https://nekobot.xyz/api/image?type=hentai"));

    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand()+"***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }
}
