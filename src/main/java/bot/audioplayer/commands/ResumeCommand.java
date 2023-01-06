package bot.audioplayer.commands;

import bot.main.CommandManager;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ResumeCommand extends Command {
    public ResumeCommand() {
        addPermission("everyone");
        addCommendPrefix("resume", "continue");
        setDescription("resumes the current paused playback");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        CommandManager.player.togglePause(event.getGuild(), false);
        MessageUtils.sendMessage(event, "resuming the playback...");
    }

}
