package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class PauseCommand extends Command {
    public PauseCommand() {
        addPermission("everyone");
        addCommendPrefix("pause");
        setDescription("pauses the current playback");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.togglePause(event.getTextChannel(), true);
    }

}
