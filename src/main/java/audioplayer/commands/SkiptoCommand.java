package audioplayer.commands;

import static main.Commands.sendBeautifulMessage;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class SkiptoCommand extends Command {
    public SkiptoCommand() {
        addPermission("everyone");
        addCommendPrefix("skipto");
        setDescription("skip songs to specific index in queue");
        setTopic("music");
        setHelpString("<number of song to skip to in the queue, type #q for queue>");
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
