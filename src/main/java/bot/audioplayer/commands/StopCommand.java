package bot.audioplayer.commands;

import bot.main.CommandManager;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StopCommand extends Command {
    public StopCommand() {
        addPermission("everyone");
        addCommendPrefix("stop");
        setDescription("stop the current playlist and remove all songs from queue");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        CommandManager.player.stop(event);
    }

}
