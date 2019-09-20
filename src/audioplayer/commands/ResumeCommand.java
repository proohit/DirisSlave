package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class ResumeCommand extends Command {
    public ResumeCommand() {
        setCommand(prefix + "resume");
        setTopic("music");
        setPermission("everyone");
        setDescription("resumes the current paused playback");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            Commands.player.togglePause(event.getTextChannel(), false);
            Commands.sendMessage(event, "resuming the playback...");

        } else {
            Commands.sendMessage(event, getHelp());
        }
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }
}
