package audioplayer.commands.playlist;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import database.PlaylistTable;
import database.Song;
import database.SongPlaylistTable;
import database.SongTable;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import static main.Commands.sendBeautifulMessage;

public class AddToPlaylistCommand extends Command {
    public AddToPlaylistCommand() {
        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 2) {
            String playlistNameToAddTo = argStrings[0];
            String search = "ytsearch: ";
            for (int i = 1; i < argStrings.length; i++) {
                search += argStrings[i] + " ";
            }
            Commands.player.fetchAudioTrack(event.getTextChannel(), search, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    if (!SongTable.hasSong(track.getInfo().uri))
                        SongTable.insertSong(new Song(track.getInfo().title, track.getInfo().uri));
                    Song song = SongTable.getSongsByUrl(track.getInfo().uri).get(0);
                    if (SongPlaylistTable.insertSongIntoPlaylist(song,
                            SongPlaylistTable.getPlaylistByName(playlistNameToAddTo)) != 0) {
                        sendBeautifulMessage(event,
                                "added \"" + track.getInfo().title + "\" to playlist " + playlistNameToAddTo);
                    } else {
                        sendBeautifulMessage(event, "there is no playlist " + playlistNameToAddTo);
                    }
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    if (!SongTable.hasSong(playlist.getTracks().get(0).getInfo().uri))
                        SongTable.insertSong(new Song(playlist.getTracks().get(0).getInfo().title,
                                playlist.getTracks().get(0).getInfo().uri));
                    Song song = SongTable.getSongsByUrl(playlist.getTracks().get(0).getInfo().uri).get(0);
                    if (SongPlaylistTable.insertSongIntoPlaylist(song,
                            PlaylistTable.getPlaylist(playlistNameToAddTo)) != 0) {
                        sendBeautifulMessage(event,
                                "added \"" + song.getTitle() + "\" to playlist " + playlistNameToAddTo);
                    } else {
                        sendBeautifulMessage(event, "there is no playlist " + playlistNameToAddTo);
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

    @Override
    protected String defineCommand() {
        return "addto";
    }

    @Override
    protected String defineDescription() {
        return "Searches for a song with the keywords and adds it to the given playlist";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "<playlist name> <keywords>";
    }

}
