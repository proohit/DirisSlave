package bot.audioplayer.commands;

import bot.main.CommandManager;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SkipCommand extends Command {
    public SkipCommand() {
        addPermission("everyone");
        addCommendPrefix("skip", "next");
        setDescription("skip current playing song");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        CommandManager.player.skipTrack(event.getGuild(), event.getChannel());
    }

}
