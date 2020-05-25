package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class PauseCommand extends Command {
    public PauseCommand() {
        setCommand(prefix + "pause");
        setTopic("music");
        addPermission("everyone");
        setDescription("pauses the current playback");
        setHelpString("");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.togglePause(event.getTextChannel(), true);
    }
}
