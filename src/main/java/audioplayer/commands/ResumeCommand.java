package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class ResumeCommand extends Command {
    public ResumeCommand() {
        addPermission("everyone");
        addCommendPrefix("resume", "continue");
        setDescription("resumes the current paused playback");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.togglePause(event.getTextChannel(), false);
        Commands.sendMessage(event, "resuming the playback...");
    }

}
