package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class JumptoCommand extends Command {
    public JumptoCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            Commands.player.jumpto(event.getTextChannel(), Integer.parseInt(argStrings[1]));
        }
    }

    @Override
    protected String defineCommand() {
        return prefix + "jumpto";
    }

    @Override
    protected String defineDescription() {
        return "sets the position of the current song";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "<position in seconds to skip to in the current playing song>";
    }
}
