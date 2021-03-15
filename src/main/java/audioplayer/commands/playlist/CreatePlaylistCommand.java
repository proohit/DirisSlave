package audioplayer.commands.playlist;

import database.Playlist;
import database.PlaylistTable;
import main.MessageUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class CreatePlaylistCommand extends Command {

    public CreatePlaylistCommand() {
        addPermission("everyone");
        addCommendPrefix("create", "new");
        setDescription("creates a new empty playlist with the given name");
        setTopic("music");
        setHelpString("<playlist name>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        long guildId = event.getGuild().getIdLong();
        String playlistName = argStrings[0];
        if (PlaylistTable.getPlaylist(playlistName, guildId) != null) {
            MessageUtils.sendBeautifulMessage(event, "this playlist has already been created before");
        }
        Playlist createdPlaylist = PlaylistTable.createPlaylist(playlistName, guildId);
        if (createdPlaylist != null) {
            MessageUtils.sendBeautifulMessage(event, "playlist " + createdPlaylist.getName() + " created.");
        }
    }

}