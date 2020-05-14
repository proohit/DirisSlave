package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import static main.Commands.sendBeautifulMessage;

public class SkiptoCommand extends Command {
    public SkiptoCommand() {
        setCommand(prefix + "skipto");
        addPermission("everyone");
        setTopic("music");
        setDescription("skip songs to specific index in queue");
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<number of song to skip to in the queue, type #q for queue>\n");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            int indexToSkipTo = Integer.parseInt(argStrings[0]);
            try {
                Commands.player.skipTo(indexToSkipTo, event.getTextChannel());
            } catch (NumberFormatException e) {
                sendBeautifulMessage(event, "the position you have entered is invalid");
            }
        } else {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
    }
}
