package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class ShuffleCommand extends Command {
    public ShuffleCommand() {
        setCommand(prefix + "shuffle");
        setPermission("everyone");
        setTopic("music");
        setDescription("bring some spice into the queue by shuffling it :man_dancing:");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (Commands.player.shuffle(event.getTextChannel())) {
            Commands.sendMessage(event, "shuffled the music :man_dancing:");
        } else {
            Commands.sendBeautifulMessage(event, "queue is empty :( start rocking with " + prefix + "pl !");
        }
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }
}
