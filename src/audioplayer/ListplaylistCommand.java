package audioplayer;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.ArrayList;

import static main.Commands.sendBeautifulMessage;

public class ListplaylistCommand extends Command {
    public ListplaylistCommand() {
        setCommand(prefix + "loadplaylist");
        setPermission("Bananenchefs");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            ArrayList<String> playlists = PlaylistManager.getPlaylists();
            if (playlists.size() == 0) {
                sendBeautifulMessage(event, "no playlists found");
                return;
            }
            StringBuilder result = new StringBuilder("saved playlists: \n");
            for (String playlist : playlists) {
                result.append(playlist).append("\n");
            }
            sendBeautifulMessage(event, result.toString());
        } else if (argStrings.length == 2) {
            ArrayList<PlaylistManager.Song> playlist = PlaylistManager.getSongsOfPlaylist(argStrings[1]);
            if (playlist.size() == 0) {
                sendBeautifulMessage(event, "no such playlist or no songs found for " + argStrings[1]);
                return;
            }
            StringBuilder result = new StringBuilder("songs of playlist " + argStrings[1] + ":\n");
            for (PlaylistManager.Song song : playlist) {
                result.append(song.getTitle()).append("\n");
            }
            sendBeautifulMessage(event, result.toString());
        }
    }
}
