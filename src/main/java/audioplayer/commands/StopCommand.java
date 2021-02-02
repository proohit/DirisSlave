package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class StopCommand extends Command {
    public StopCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.stop(event);
    }

    @Override
    protected String[] defineCommand() {
        return new String[] { "stop" };
    }

    @Override
    protected String defineDescription() {
        return "stop the current playlist and remove all songs from queue";
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
