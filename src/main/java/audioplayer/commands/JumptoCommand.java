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
    }

    @Override
    public String getHelp() {
        final StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<position in seconds to skip in the current song>");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            Commands.player.jumpto(event.getTextChannel(), Integer.parseInt(argStrings[1]));
        }
    }
}
