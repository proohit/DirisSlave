package bot.audioplayer.commands;

import bot.main.CommandManager;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PauseCommand extends Command {
    public PauseCommand() {
        addPermission("everyone");
        addCommendPrefix("pause");
        setDescription("pauses the current playback");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        CommandManager.player.togglePause(event.getGuild(), true);
    }

}
