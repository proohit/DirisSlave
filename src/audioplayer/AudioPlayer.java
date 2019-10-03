package audioplayer;

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
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
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
        if(setPause == true) {
            musicManager.scheduler.pause();
        } else if(setPause == false) {
            musicManager.scheduler.resume();
        }
    }
    public void fetchAudioTrack(final TextChannel channel, final String trackUrl, AudioLoadResultHandler handler) {
        lastManager = channel.getGuild().getAudioManager();
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        playerManager.loadItemOrdered(musicManager, trackUrl, handler);
    }
    public boolean setRepeat(TextChannel channel,boolean repeat) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        return musicManager.scheduler.setRepeat(repeat);
    }
    public boolean playPlaylist(final TextChannel channel, final String playlist) {
        lastManager = channel.getGuild().getAudioManager();
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        ArrayList<Song> songs = (ArrayList<Song>) SongPlaylistTable.getSongsByPlaylist(playlist);
        if(songs.size() == 0) {
            return false;
        }
        for (Song song : songs) {
            playerManager.loadItemOrdered(musicManager, song.getUrl(), new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    play(channel.getGuild(), musicManager, track);
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    play(channel.getGuild(), musicManager, playlist.getTracks().get(0));
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

    public void loadAndPlay(final TextChannel channel, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        lastManager = channel.getGuild().getAudioManager();
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                float seconds = track.getDuration()/1000;
                int minutes = (int)seconds/60;
                seconds = seconds%60;
                channel.sendMessage("Adding to queue " + track.getInfo().title+ " Duration: " + minutes + ":"+seconds + " minutes").queue();

                play(channel.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }
                int seconds = (int)firstTrack.getDuration()/1000;
                int minutes = (int)seconds/60;
                seconds = seconds%60;
                String secondsString = String.valueOf(seconds);
                String minutesString = String.valueOf(minutes);
                if(secondsString.length() <2) secondsString="0" + secondsString;
                if(minutesString.length() <2) minutesString = "0" + minutesString;
                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " Duration: " + minutesString + ":"+secondsString + " minutes").queue();

                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    public Stream<AudioTrack> getQueue(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        return musicManager.scheduler.getQueue();
    }

    public void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public void stop(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.stop();
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
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            audioManager.openAudioConnection(channel);
        }
    }
}