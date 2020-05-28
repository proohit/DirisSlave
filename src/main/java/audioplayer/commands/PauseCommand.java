package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class PauseCommand extends Command {
    public PauseCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.togglePause(event.getTextChannel(), true);
    }

    @Override
    protected String defineCommand() {
        return prefix + "pause";
    }

    @Override
    protected String defineDescription() {
        return "pauses the current playback";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "";
    }
}
