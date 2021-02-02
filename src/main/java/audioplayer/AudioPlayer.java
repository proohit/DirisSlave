package audioplayer;

import java.util.ArrayList;
import java.util.HashMap;
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

    public boolean playPlaylist(final TextChannel channel, final String playlist) {
        lastManager = channel.getGuild().getAudioManager();
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        ArrayList<Song> songs = (ArrayList<Song>) SongPlaylistTable.getSongsByPlaylist(playlist);
        if (songs.isEmpty()) {
            return false;
        }
        for (Song song : songs) {
            playerManager.loadItemOrdered(musicManager, song.getUrl(), new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    play(musicManager, track);
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    play(musicManager, playlist.getTracks().get(0));
                }

                @Override
                public void noMatches() {
                    channel.sendMessage("could not load \"" + song.getTitle() + "\"");
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    channel.sendMessage("could not load \"" + song.getTitle() + "\"");
                }
            });
        }
        return true;
    }

    public void loadAndPlay(final MessageReceivedEvent event, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
        lastManager = event.getGuild().getAudioManager();
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
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
        });
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

        channel.sendMessage("Skipped to next track.").queue();
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

}