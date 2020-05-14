package audioplayer.commands.playlist;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class PlaylistCommand extends Command {
    public PlaylistCommand() {
        setCommand(prefix + "playlist");
        setTopic("music");
        setDescription("lists options regarding playlists");

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
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("list [playlist name, optional]\n");
        help.append("create <playlist name>\n");
        help.append("load <playlist name>\n");
        help.append("delete <playlist name>\n");
        help.append("addto <playlist name> <keywords>\n");
        help.append(
                "remove <playlist name> <index of song in playlist. type playlist list playlistname to get indexes>\n");
        help.append("savehistory <playlist name>\n");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {

    }
}
