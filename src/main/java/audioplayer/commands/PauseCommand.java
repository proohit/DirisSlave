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
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.togglePause(event.getTextChannel(), true);
        Commands.sendMessage(event, "pausing the playback...");
    }
}
