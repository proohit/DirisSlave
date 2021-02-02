package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class RemoveCommand extends Command {
    public RemoveCommand() {
        addPermission("everyone");
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

    @Override
    protected String[] defineCommand() {
        return new String[] { "remove", "rm" };
    }

    @Override
    protected String defineDescription() {
        return "remove song from queue";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "<number of song in queue> type " + prefix + "q to see queue";
    }
}
