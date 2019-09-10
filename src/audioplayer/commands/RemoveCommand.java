package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class RemoveCommand extends Command {
    public RemoveCommand() {
        setCommand(prefix + "remove");
        setPermission("everyone");
        setTopic("music");
        setDescription("remove song from queue");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if(argStrings.length < 2) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        if (argStrings.length == 2) {
            try {
                Commands.player.remove(Integer.parseInt(argStrings[1]), event.getTextChannel());
            } catch (NumberFormatException e) {
                Commands.sendBeautifulMessage(event, "the position you have entered is invalid");
            }
        }
    }
    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand()+"***");
        help.append(" - " + getDescription() + "\n");

        help.append("<number of song in queue, type #q for queue>\n");

        return help.toString();
    }
}
