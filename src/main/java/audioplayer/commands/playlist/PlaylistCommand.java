package audioplayer.commands.playlist;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class PlaylistCommand extends Command {
    public PlaylistCommand() {
        this.addSubCommand(new CreatePlaylistCommand());
        this.addSubCommand(new DeletePlaylistCommand());
        this.addSubCommand(new LoadPlaylistCommand());
        this.addSubCommand(new ListPlaylistCommand());
        this.addSubCommand(new SaveHistoryCommand());
        this.addSubCommand(new AddToPlaylistCommand());
        this.addSubCommand(new RemoveFromPlaylistCommand());

        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {

    }

    @Override
    protected String defineCommand() {
        return prefix + "playlist";
    }

    @Override
    protected String defineDescription() {
        return "lists options regarding playlists";
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
