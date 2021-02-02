package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class ResumeCommand extends Command {
    public ResumeCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.togglePause(event.getTextChannel(), false);
        Commands.sendMessage(event, "resuming the playback...");
    }

    @Override
    protected String[] defineCommand() {
        return new String[] { "resume", "continue" };
    }

    @Override
    protected String defineDescription() {
        return "resumes the current paused playback";
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
