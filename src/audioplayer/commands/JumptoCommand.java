package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class JumptoCommand extends Command {
    public JumptoCommand() {
        setCommand(prefix+"jumpto");
        setPermission("Bananenchefs");
        setTopic("music");
        setDescription("sets the position of the current song");
    }
    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if(argStrings.length == 2) {
            Commands.player.jumpto(event.getTextChannel(), Integer.parseInt(argStrings[1]));
        }
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<position in seconds to skip in the current song>");

        return help.toString();
    }
}
