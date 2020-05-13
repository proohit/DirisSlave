package audioplayer.commands.playlist;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class CreatePlaylistCommand extends Command {

    public CreatePlaylistCommand() {
        this.setCommand("create");
        this.setDescription("description");
        this.setHelpString("");
        this.setPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        // TODO Auto-generated method stub

    }
}