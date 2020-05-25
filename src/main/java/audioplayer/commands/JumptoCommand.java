package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class JumptoCommand extends Command {
    public JumptoCommand() {
        setCommand(prefix + "jumpto");
        addPermission("everyone");
        setTopic("music");
        setDescription("sets the position of the current song");
        setHelpString("<position in seconds to skip to in the current playing song>");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            Commands.player.jumpto(event.getTextChannel(), Integer.parseInt(argStrings[1]));
        }
    }
}
