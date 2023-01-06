package bot.audioplayer.commands.playlist;

import java.util.ArrayList;

import bot.database.Playlist;
import bot.database.PlaylistTable;
import bot.database.SongPlaylistTable;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;;

public class ListPlaylistCommand extends Command {
    public ListPlaylistCommand() {
        addPermission("everyone");
        addCommendPrefix("list", "ls");
        setDescription("lists all playlists or the songs of a playlist");
        setTopic("music");
        setHelpString("[playlist name, optional]");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        long guildId = event.getGuild().getIdLong();
        if (argStrings.length == 0) {
            ArrayList<Playlist> playlists = (ArrayList<Playlist>) PlaylistTable.getPlaylists(guildId);
            if (playlists.isEmpty()) {
                MessageUtils.sendBeautifulMessage(event, "no playlists found");
                return;
            }
            StringBuilder result = new StringBuilder("saved playlists: \n");
            playlists.stream().forEach(playlist -> result.append(playlist.getName()).append("\n"));
            MessageUtils.sendBeautifulMessage(event, result.toString());
        } else if (argStrings.length >= 1) {
            String playlistToList = argStrings[0];
            Playlist playlist = SongPlaylistTable.getPlaylistByName(playlistToList, guildId);
            if (playlist.getSongs().isEmpty()) {
                MessageUtils.sendBeautifulMessage(event, "no such playlist or no songs found for " + playlistToList);
                return;
            }
            StringBuilder result = new StringBuilder("songs of playlist " + playlistToList + ":\n");
            for (int i = 1; i <= playlist.getSongs().size(); i++) {
                result.append(i + " ").append(playlist.getSongs().get(i - 1).getTitle()).append("\n");
            }
            MessageUtils.sendBeautifulMessage(event, result.toString());
        }
    }

}
