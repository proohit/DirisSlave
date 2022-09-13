package audioplayer.commands;

import main.CommandManager;
import main.MessageUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class SkiptoCommand extends Command {
    public SkiptoCommand() {
        addPermission("everyone");
        addCommendPrefix("skipto");
        setDescription("skip songs to specific index in queue");
        setTopic("music");
        setHelpString("<number of song to skip to in the queue, type #q for queue>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        int indexToSkipTo = Integer.parseInt(argStrings[0]);
        try {
            CommandManager.player.skipTo(indexToSkipTo, event.getChannel().asTextChannel());
        } catch (NumberFormatException e) {
            MessageUtils.sendBeautifulMessage(event, "the position you have entered is invalid");
        }
    }

}
