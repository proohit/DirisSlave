package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class SkipCommand extends Command {
    public SkipCommand() {
        setCommand(prefix+"skip");
        setPermission("Bananenchefs");
        setTopic("music");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.skipTrack(event.getTextChannel());
    }
}
