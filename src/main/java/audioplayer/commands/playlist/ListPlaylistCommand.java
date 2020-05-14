package audioplayer.commands.playlist;

import java.util.ArrayList;

import database.Playlist;
import database.PlaylistTable;
import database.SongPlaylistTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import static main.Commands.sendBeautifulMessage;;

public class ListPlaylistCommand extends Command {
    public ListPlaylistCommand() {
        this.setCommand("list");
        this.setDescription("");
        this.setHelpString("");
        this.setTopic("music");

        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 0) {
            ArrayList<Playlist> playlists = (ArrayList) PlaylistTable.getPlaylists();
            if (playlists.size() == 0) {
                sendBeautifulMessage(event, "no playlists found");
                return;
            }
            StringBuilder result = new StringBuilder("saved playlists: \n");
            playlists.stream().forEach(playlist -> result.append(playlist.getName()).append("\n"));
            sendBeautifulMessage(event, result.toString());
        } else if (argStrings.length == 1) {
            String playlistToList = argStrings[0];
            Playlist playlist = SongPlaylistTable.getPlaylistByName(playlistToList);
            if (playlist.getSongs().size() == 0) {
                sendBeautifulMessage(event, "no such playlist or no songs found for " + playlistToList);
                return;
            }
            StringBuilder result = new StringBuilder("songs of playlist " + playlistToList + ":\n");
            for (int i = 1; i <= playlist.getSongs().size(); i++) {
                result.append(i + " ").append(playlist.getSongs().get(i - 1).getTitle()).append("\n");
            }
            sendBeautifulMessage(event, result.toString());
        }
    }

}
