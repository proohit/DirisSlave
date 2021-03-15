package audioplayer.commands;

import main.CommandManager;
import main.MessageUtils;
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
        CommandManager.player.togglePause(event.getTextChannel(), false);
        MessageUtils.sendMessage(event, "resuming the playback...");
    }

}
