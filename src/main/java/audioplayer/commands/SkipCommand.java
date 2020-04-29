package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class SkipCommand extends Command {
    public SkipCommand() {
        setCommand(prefix + "skip");
        setPermission("everyone");
        setTopic("music");
        setDescription("skip current playing song");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.skipTrack(event.getTextChannel());
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }
}