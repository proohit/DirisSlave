package audioplayer.commands;

import main.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class RemoveCommand extends Command {
    public RemoveCommand() {
        addPermission("everyone");
        addCommendPrefix("remove", "rm");
        setDescription("remove song from queue");
        setTopic("music");
        setHelpString("<number of song in queue>. use queue command to see queue");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        int indexToRemove = Integer.parseInt(argStrings[0]);
        try {
            CommandManager.player.remove(indexToRemove, event.getTextChannel());
        } catch (NumberFormatException e) {
            CommandManager.sendBeautifulMessage(event, "the position you have entered is invalid");
        }
    }

}
