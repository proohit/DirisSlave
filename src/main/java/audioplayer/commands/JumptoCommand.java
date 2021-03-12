package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class JumptoCommand extends Command {
    public JumptoCommand() {
        addPermission("everyone");
        addCommendPrefix("jumpto");
        setDescription("sets the position of the current song");
        setTopic("music");
        setHelpString("<position in seconds to skip to in the current playing song>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Commands.player.jumpto(event.getTextChannel(), Integer.parseInt(argStrings[1]));
    }

}
