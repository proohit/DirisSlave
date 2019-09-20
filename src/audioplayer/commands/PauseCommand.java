package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class PauseCommand extends Command {
    public PauseCommand() {
        setCommand(prefix+"pause");
        setTopic("music");
        setPermission("everyone");
        setDescription("pauses the current playback");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if(argStrings.length==1) {
            Commands.player.togglePause(event.getTextChannel(), true);
            Commands.sendMessage(event, "pausing the playback...");
        } else {
            Commands.sendMessage(event, getHelp());
        }
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand()+"***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }
}
