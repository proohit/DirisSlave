package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import shared.commands.Command;

public class StopCommand extends Command {
    public StopCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        AudioManager manager = event.getGuild().getAudioManager();
        if (manager.isConnected() || manager.isAttemptingToConnect())
            manager.closeAudioConnection();
        Commands.player.stop(event.getTextChannel());
    }

    @Override
    protected String defineCommand() {
        return prefix + "stop";
    }

    @Override
    protected String defineDescription() {
        return "stop the current playlist and remove all songs from queue";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "";
    }
}
