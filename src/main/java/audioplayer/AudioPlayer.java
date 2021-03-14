package audioplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import database.Song;
import database.SongPlaylistTable;
import main.Commands;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class AudioPlayer {

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;
    private static AudioManager lastManager;

    public AudioPlayer() {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager;

        if (!musicManagers.containsKey(guildId)) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        } else {
            musicManager = musicManagers.get(guildId);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public boolean shuffle(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        return musicManager.scheduler.shuffle();
    }

    public void togglePause(TextChannel channel, boolean setPause) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        if (setPause) {
            musicManager.scheduler.pause();
        } else {
            musicManager.scheduler.resume();
        }
    }

    public void fetchAudioTrack(final TextChannel channel, final String trackUrl, AudioLoadResultHandler handler) {
        lastManager = channel.getGuild().getAudioManager();
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        playerManager.loadItemOrdered(musicManager, trackUrl, handler);
    }

    public boolean setRepeat(TextChannel channel, boolean repeat) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        return musicManager.scheduler.setRepeat(repeat);
    }

    public void playPlaylist(final TextChannel channel, final String playlistName, final boolean shuffle) {
        lastManager = channel.getGuild().getAudioManager();
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        ArrayList<Song> songs = (ArrayList<Song>) SongPlaylistTable.getSongsByPlaylist(playlistName,
                channel.getGuild().getIdLong());
        if (songs.isEmpty()) {
            Commands.sendBeautifulMessage(channel, String.format("Playlist %s empty or not found", playlistName));
            return;
        }
        if (shuffle) {
            Collections.shuffle(songs);
        }
        List<Integer> completedSongIds = new ArrayList<>();
        for (Song song : songs) {
            playerManager.loadItemOrdered(musicManager, song.getUrl(),
                    new PlaylistLoadedHandler(musicManager, completedSongIds, song, playlistName, channel, songs));
        }
    }

    public void loadAndPlay(final MessageReceivedEvent event, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
        lastManager = event.getGuild().getAudioManager();
        playerManager.loadItemOrdered(musicManager, trackUrl,
                new SingleSongLoadedHandler(musicManager, trackUrl, event));
    }

    public Stream<AudioTrack> getQueue(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        return musicManager.scheduler.getQueue();
    }

    public void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public void stop(MessageReceivedEvent event) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.stop();
        AudioManager manager = event.getGuild().getAudioManager();
        if (manager.isConnected()) {
            manager.closeAudioConnection();
        }
    }

    public long seek(TextChannel channel, int seconds) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        return musicManager.scheduler.seek(seconds);
    }

    public void jumpto(TextChannel channel, int seconds) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.jumpto(seconds);
    }

    public void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        Commands.sendBeautifulMessage(channel, "Skipped to next track.");
    }

    public void remove(int pos, TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.remove(pos);
    }

    public static AudioManager getLastManager() {
        return lastManager;
    }

    public void skipTo(int pos, TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.skipTo(pos);
    }

    public static void connectToUserVoiceChannel(AudioManager audioManager, VoiceChannel channel) {
        if (!audioManager.isConnected()) {
            audioManager.openAudioConnection(channel);
        }
    }

    private final class SingleSongLoadedHandler implements AudioLoadResultHandler {
        private final String trackUrl;
        private final MessageReceivedEvent event;
        private final GuildMusicManager musicManager;

        private SingleSongLoadedHandler(GuildMusicManager musicManager, String trackUrl, MessageReceivedEvent event) {
            this.trackUrl = trackUrl;
            this.event = event;
            this.musicManager = musicManager;
        }

        @Override
        public void trackLoaded(AudioTrack track) {
            SearchResultEmbed searchResultEmbed = new SearchResultEmbed(track);
            event.getTextChannel().sendMessage(searchResultEmbed.getEmbed()).queue();
            AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(),
                    event.getMember().getVoiceState().getChannel());
            play(musicManager, track);
        }

        @Override
        public void playlistLoaded(AudioPlaylist playlist) {
            AudioTrack firstTrack = playlist.getSelectedTrack();

            if (firstTrack == null) {
                firstTrack = playlist.getTracks().get(0);
            }
            SearchResultEmbed searchResultEmbed = new SearchResultEmbed(firstTrack);
            event.getTextChannel().sendMessage(searchResultEmbed.getEmbed()).queue();
            AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(),
                    event.getMember().getVoiceState().getChannel());
            play(musicManager, firstTrack);
        }

        @Override
        public void noMatches() {
            event.getTextChannel().sendMessage("Nothing found by " + trackUrl).queue();
        }

        @Override
        public void loadFailed(FriendlyException exception) {
            event.getTextChannel().sendMessage("Could not play: " + exception.getMessage()).queue();
        }
    }

    private final class PlaylistLoadedHandler implements AudioLoadResultHandler {
        private final GuildMusicManager musicManager;
        private final List<Integer> completedSongIds;
        private final Song song;
        private final String playlistName;
        private final TextChannel channel;
        private final ArrayList<Song> songs;

        private PlaylistLoadedHandler(GuildMusicManager musicManager, List<Integer> completedSongIds, Song song,
                String playlistName, TextChannel channel, ArrayList<Song> songs) {
            this.musicManager = musicManager;
            this.completedSongIds = completedSongIds;
            this.song = song;
            this.playlistName = playlistName;
            this.channel = channel;
            this.songs = songs;
        }

        @Override
        public void trackLoaded(AudioTrack track) {
            completedSongIds.add(song.getId());
            play(musicManager, track);
            if (completedSongIds.size() == songs.size()) {
                Commands.sendBeautifulMessage(channel, String.format("Loaded Playlist %s", playlistName));
            }
        }

        @Override
        public void playlistLoaded(AudioPlaylist playlist) {
            AudioTrack track = playlist.getTracks().get(0);
            completedSongIds.add(song.getId());
            play(musicManager, track);
            if (completedSongIds.size() == songs.size()) {
                Commands.sendBeautifulMessage(channel, String.format("Loaded Playlist %s", playlistName));
            }
        }

        @Override
        public void noMatches() {
            completedSongIds.add(song.getId());
            channel.sendMessage("could not load \"" + song.getTitle() + "\"");
            if (completedSongIds.size() == songs.size()) {
                Commands.sendBeautifulMessage(channel, String.format("No match found for %s", song.getTitle()));
            }
        }

        @Override
        public void loadFailed(FriendlyException exception) {
            completedSongIds.add(song.getId());
            channel.sendMessage("could not load \"" + song.getTitle() + "\"");
            if (completedSongIds.size() == songs.size()) {
                Commands.sendBeautifulMessage(channel, String.format("Error loading %s", song.getTitle()));
            }
        }
    }
}