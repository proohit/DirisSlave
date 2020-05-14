package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import util.Command;

public class StopCommand extends Command {
    public StopCommand() {
        setCommand(prefix + "stop");
        addPermission("everyone");
        setTopic("music");
        setDescription("stop the current playlist and remove all songs from queue");
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
        AudioManager manager = event.getGuild().getAudioManager();
        if (manager.isConnected() || manager.isAttemptingToConnect())
            manager.closeAudioConnection();
        Commands.player.stop(event.getTextChannel());
    }
}
