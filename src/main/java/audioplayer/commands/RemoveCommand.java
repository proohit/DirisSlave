package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class RemoveCommand extends Command {
    public RemoveCommand() {
        setCommand(prefix + "remove");
        addPermission("everyone");
        setTopic("music");
        setDescription("remove song from queue");
        setHelpString("<number of song in queue> type " + prefix + "q to see queue");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length < 1) {
            main.Commands.sendMessage(event, getHelp());
            return;
        } else {
            int indexToRemove = Integer.parseInt(argStrings[0]);
            try {
                Commands.player.remove(indexToRemove, event.getTextChannel());
            } catch (NumberFormatException e) {
                Commands.sendBeautifulMessage(event, "the position you have entered is invalid");
            }
        }
    }
}
