package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class SkipCommand extends Command {
    public SkipCommand() {
        addPermission("everyone");
        addCommendPrefix("skip", "next");
        setDescription("skip current playing song");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.skipTrack(event.getTextChannel());
    }

}
