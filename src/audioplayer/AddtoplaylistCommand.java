package audioplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import static main.Commands.sendBeautifulMessage;

public class AddtoplaylistCommand extends Command {
    public AddtoplaylistCommand() {
        setCommand(prefix + "addtoplaylist");
        setPermission("Bananenchefs");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 3) {
            String search = "ytsearch: ";
            for (int i = 2; i < argStrings.length; i++) {
                search += argStrings[i] + " ";
            }
            Commands.player.fetchAudioTrack(event.getTextChannel(), search, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    if (PlaylistManager.addToPlaylist(argStrings[1], track)) {
                        sendBeautifulMessage(event, "added \"" + track.getInfo().title + "\" to playlist " + argStrings[1]);
                    } else {
                        sendBeautifulMessage(event, "there is no playlist " + argStrings[1]);
                    }
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    AudioTrack firstTrack = playlist.getTracks().get(0);
                    if (PlaylistManager.addToPlaylist(argStrings[1], firstTrack)) {
                        sendBeautifulMessage(event, "added \"" + firstTrack.getInfo().title + "\" to playlist " + argStrings[1]);
                    } else {
                        sendBeautifulMessage(event, "there is no playlist " + argStrings[1]);
                    }
                }

                @Override
                public void noMatches() {
                    sendBeautifulMessage(event, "Nothing found for keywords");
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    System.out.println("loadFailed");
                }
            });
        }
    }
}
