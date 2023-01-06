package bot.audioplayer.commands.playlist;

import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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
        addCommendPrefix("playlist");
        setDescription("lists options regarding playlists");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        return;
    }

}
