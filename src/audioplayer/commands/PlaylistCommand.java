package audioplayer.commands;

import audioplayer.AudioPlayer;
import audioplayer.PlaylistManager;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import database.*;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.ArrayList;

import static main.Commands.sendBeautifulMessage;

public class PlaylistCommand extends Command {
    public PlaylistCommand() {
        setCommand(prefix + "playlist");
        setPermission("everyone");
        setTopic("music");
        setDescription("lists options regarding playlists");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if(argStrings.length <= 1) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        if (argStrings[1].equals("create")) {
            if (argStrings.length == 3) {
                if (PlaylistTable.createPlaylist(argStrings[2])==1) {
                    main.Commands.sendBeautifulMessage(event, "playlist " + argStrings[2] + " created.");
                } else {
                    main.Commands.sendBeautifulMessage(event, "this playlist has already been created before");
                }
            }
        } else if (argStrings[1].equals("delete")) {
            if (argStrings.length == 3) {
                String result;
                result = PlaylistTable.deletePlaylist(argStrings[2]) == 1? "deleted playlist " + argStrings[2] : "playlist " + argStrings[2] + " not found";
                sendBeautifulMessage(event, result);
            }
        } else if (argStrings[1].equals("load")) {
            if (argStrings.length == 3) {
                if (Commands.player.playPlaylist(event.getTextChannel(), argStrings[2])) {
                    AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(), event.getMember().getVoiceState().getChannel());
                    Commands.sendBeautifulMessage(event, "loaded playlist " + argStrings[2]);
                } else {
                    main.Commands.sendBeautifulMessage(event, argStrings[2] + " not found or no songs available");
                }
            }
        } else if (argStrings[1].equals("list")) {
            if (argStrings.length == 2) {
                ArrayList<Playlist> playlists = (ArrayList) PlaylistTable.getPlaylists();
                if (playlists.size() == 0) {
                    sendBeautifulMessage(event, "no playlists found");
                    return;
                }
                StringBuilder result = new StringBuilder("saved playlists: \n");
                playlists.stream().forEach(playlist -> result.append(playlist.getName()).append("\n"));
                sendBeautifulMessage(event, result.toString());
            } else if (argStrings.length == 3) {
                Playlist playlist = SongPlaylistTable.getPlaylistByName(argStrings[2]);
                if (playlist.getSongs().size() == 0) {
                    sendBeautifulMessage(event, "no such playlist or no songs found for " + argStrings[2]);
                    return;
                }
                StringBuilder result = new StringBuilder("songs of playlist " + argStrings[2] + ":\n");
                for(int i = 1;i<=playlist.getSongs().size();i++) {
                    result.append(i + " ").append(playlist.getSongs().get(i-1).getTitle()).append("\n");
                }
                sendBeautifulMessage(event, result.toString());
            }
        } else if (argStrings[1].equals("savehistory")) {
            if (argStrings.length >= 3) {
                PlaylistManager.savePlaylistFromHistory(argStrings[2]);
                sendBeautifulMessage(event, "saved playlist " + argStrings[2]);
            }
        } else if (argStrings[1].equals("addto")) {
            if (argStrings.length >= 4) {
                String search = "ytsearch: ";
                for (int i = 3; i < argStrings.length; i++) {
                    search += argStrings[i] + " ";
                }
                Commands.player.fetchAudioTrack(event.getTextChannel(), search, new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack track) {
                        if(!SongTable.hasSong(track.getInfo().uri)) SongTable.insertSong(new Song(track.getInfo().title,track.getInfo().uri));
                        Song song = SongTable.getSongsByUrl(track.getInfo().uri).get(0);
                        if (SongPlaylistTable.insertSongIntoPlaylist(song, SongPlaylistTable.getPlaylistByName(argStrings[2])) != 0) {
                            sendBeautifulMessage(event, "added \"" + track.getInfo().title + "\" to playlist " + argStrings[2]);
                        } else {
                            sendBeautifulMessage(event, "there is no playlist " + argStrings[2]);
                        }
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {
                        if(!SongTable.hasSong(playlist.getTracks().get(0).getInfo().uri)) SongTable.insertSong(new Song(playlist.getTracks().get(0).getInfo().title,playlist.getTracks().get(0).getInfo().uri));
                        Song song = SongTable.getSongsByUrl(playlist.getTracks().get(0).getInfo().uri).get(0);
                        if (SongPlaylistTable.insertSongIntoPlaylist(song, PlaylistTable.getPlaylist(argStrings[2])) != 0) {
                            sendBeautifulMessage(event, "added \"" + song.getTitle() + "\" to playlist " + argStrings[2]);
                        } else {
                            sendBeautifulMessage(event, "there is no playlist " + argStrings[2]);
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
        } else if(argStrings[1].equals("remove")) {
            if(argStrings.length == 4) {
                Song song = SongPlaylistTable.getPlaylistByName(argStrings[2]).getSongs().get(Integer.parseInt(argStrings[3])-1);
                if(song!=null) {
                    if(SongPlaylistTable.removeSongFromPlaylist(argStrings[2], song) != 0) {
                        sendBeautifulMessage(event, "deleted " + song.getTitle() + " from " + argStrings[2]);
                    }
                } else {
                    sendBeautifulMessage(event, "playlist or song not found.");
                }
            }
        }
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("list [playlist name, optional]\n");
        help.append("create <playlist name>\n");
        help.append("load <playlist name>\n");
        help.append("delete <playlist name>\n");
        help.append("addto <playlist name> <keywords>\n");
        help.append("remove <playlist name> <index of song in playlist. type playlist list playlistname to get indexes>\n");
        help.append("savehistory <playlist name>\n");

        return help.toString();
    }
}
