package audioplayer.commands;

import main.CommandManager;
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
        CommandManager.player.skipTrack(event.getChannel().asTextChannel());
    }

}
