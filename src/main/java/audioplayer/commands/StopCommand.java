package audioplayer.commands;

import main.CommandManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

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

    public void handle(Guild guild) {
        CommandManager.player.stop(guild);
    }

}
