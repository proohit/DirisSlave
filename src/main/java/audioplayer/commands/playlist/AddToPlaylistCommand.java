package audioplayer.commands.playlist;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import database.PlaylistTable;
import database.Song;
import database.SongPlaylistTable;
import database.SongTable;
import main.CommandManager;
import main.MessageUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class AddToPlaylistCommand extends Command {
    public AddToPlaylistCommand() {
        addPermission("everyone");
        addCommendPrefix("addto");
        setDescription("Searches for a song with the keywords and adds it to the given playlist");
        setTopic("music");
        setHelpString("<playlist name> <keywords>");
        setMinArguments(2);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        long guildId = event.getGuild().getIdLong();
        String playlistNameToAddTo = argStrings[0];
        String search = "ytsearch: ";
        for (int i = 1; i < argStrings.length; i++) {
            search += argStrings[i] + " ";
        }
        CommandManager.player.fetchAudioTrack(event.getChannel().asTextChannel(), search, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                if (!SongTable.hasSong(track.getInfo().uri))
                    SongTable.insertSong(new Song(track.getInfo().title, track.getInfo().uri));
                Song song = SongTable.getSongsByUrl(track.getInfo().uri).get(0);
                if (SongPlaylistTable.insertSongIntoPlaylist(song,
                        SongPlaylistTable.getPlaylistByName(playlistNameToAddTo, guildId)) != 0) {
                    MessageUtils.sendBeautifulMessage(event,
                            "added \"" + track.getInfo().title + "\" to playlist " + playlistNameToAddTo);
                } else {
                    MessageUtils.sendBeautifulMessage(event, "there is no playlist " + playlistNameToAddTo);
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (!SongTable.hasSong(playlist.getTracks().get(0).getInfo().uri))
                    SongTable.insertSong(new Song(playlist.getTracks().get(0).getInfo().title,
                            playlist.getTracks().get(0).getInfo().uri));
                Song song = SongTable.getSongsByUrl(playlist.getTracks().get(0).getInfo().uri).get(0);
                if (SongPlaylistTable.insertSongIntoPlaylist(song,
                        PlaylistTable.getPlaylist(playlistNameToAddTo, guildId)) != 0) {
                    MessageUtils.sendBeautifulMessage(event,
                            "added \"" + song.getTitle() + "\" to playlist " + playlistNameToAddTo);
                } else {
                    MessageUtils.sendBeautifulMessage(event, "there is no playlist " + playlistNameToAddTo);
                }
            }

            @Override
            public void noMatches() {
                MessageUtils.sendBeautifulMessage(event, "Nothing found for keywords");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.out.println("loadFailed");
            }
        });
    }

}
