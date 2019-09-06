package audioplayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.Commands;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaylistManager {
    public static class Song {
        String title;
        String url;
        Song(String title, String url) {
            this.title=title;
            this.url=url;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }
    }

    private static File getFile() {
        File playlistFile = new File("playlistplayer.txt");
        try {
            playlistFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playlistFile;
    }

    ;

    public static ArrayList<String> getHistory() {
        ArrayList<String> historyItems = new ArrayList<String>();
        try {
            String[] content = new String(Files.readAllBytes(Paths.get("playlistplayer.txt"))).split("\n");
            boolean foundHistory = false;
            for (String line : content) {
                if (line.contains("History:")) {
                    foundHistory = true;
                    continue;
                }
                if (foundHistory == true) {
                    historyItems.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historyItems;
    }

    public static ArrayList<String> getHistory(String[] fileContent) {
        ArrayList<String> historyItems = new ArrayList<String>();
        boolean foundHistory = false;
        for (String line : fileContent) {
            if (line.contains("History:")) {
                foundHistory = true;
                continue;
            }
            if (foundHistory == true) {
                foundHistory = true;
                historyItems.add(line);
            }
        }

        return historyItems;
    }


    public static void saveHistory() {

    }

    public static void writeHistoryItem(String url) {
        try {
            FileWriter fw = new FileWriter(getFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(url + "\n");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPlaylist(String name, MessageReceivedEvent event) {
        try {
            String[] content = new String(Files.readAllBytes(Paths.get("playlistplayer.txt"))).split("\n");
            boolean foundPlaylist = false;
            for (String line : content) {
                if (line.contains("playlist " + name)) {
                    foundPlaylist = true;
                    continue;
                }
                if (line.contains("---")) {
                    break;
                }
                if (foundPlaylist == true) {
                    String[] song = line.split("-/-");
                    //url bei song[1]
                    AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(), event.getMember().getVoiceState().getChannel());
                    Commands.player.loadAndPlay(event.getTextChannel(), song[1]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> getPlaylists() {
        ArrayList<String> playlists = new ArrayList<String>();
        try {
            String[] content = new String(Files.readAllBytes(Paths.get("playlistplayer.txt"))).split("\n");
            for (String line : content) {
                if (line.matches("playlist ([a-z]|[A-Z]|[0-9])*:")) {
                    String[] lineContent = line.split("playlist ");
                    playlists.add(lineContent[1].replaceAll(":", ""));
                }
            }
        } catch (IOException e) {
        }
        return playlists;
    }

    public static boolean addToPlaylist(String playlist, AudioTrack track) {
        boolean hasFoundPlaylist = false;
        try {
            String[] content = new String(Files.readAllBytes(Paths.get("playlistplayer.txt"))).split("\n");
            FileWriter fw = new FileWriter(getFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            String newFile = "";
            for (String line : content) {
                newFile += line + "\n";
                if (line.contains("playlist " + playlist + ":")) {
                    hasFoundPlaylist = true;
                    newFile += track.getInfo().title + "-/-" + track.getInfo().uri + "\n";
                }
            }
            bw.write(newFile);
            bw.close();
            fw.close();
        } catch (IOException e) {

        }
        return hasFoundPlaylist;
    }

    public static boolean deletePlaylist(String playlist) {
        boolean isPlaylistAvailable = false;
        try {
            String[] content = new String(Files.readAllBytes(Paths.get("playlistplayer.txt"))).split("\n");
            FileWriter fw = new FileWriter(getFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            String newFile = "";
            boolean hasFoundPlaylist = false;
            for (String line : content) {
                if (line.equals("playlist " + playlist + ":")) {
                    hasFoundPlaylist = true;
                    isPlaylistAvailable = true;
                } else if (line.equals("---") && hasFoundPlaylist) {
                    hasFoundPlaylist = false;
                } else if (hasFoundPlaylist) {

                } else {
                    newFile += line + "\n";
                }
            }
            bw.write(newFile);
            bw.close();
            fw.close();
        } catch (IOException e) {

        }
        return isPlaylistAvailable;
    }

    public static ArrayList<Song> getSongsOfPlaylist(String playlist) {
        ArrayList<Song> playlists = new ArrayList<>();
        try {
            String[] content = new String(Files.readAllBytes(Paths.get("playlistplayer.txt"))).split("\n");
            boolean hasFoundPlaylist = false;
            for (String line : content) {
                if (line.matches("playlist " + playlist + ":")) {
                    hasFoundPlaylist = true;
                    continue;
                } else if (line.contains("---") && hasFoundPlaylist) {
                    break;
                } else if (hasFoundPlaylist) {
                    String title = line.split("-/-")[0];
                    String url = line.split("-/-")[1];
                    playlists.add(new Song(title,url));
                }
            }
        } catch (IOException e) {

        }
        return playlists;
    }

    public static void savePlaylistFromHistory(String name) {
        try {
            String[] content = new String(Files.readAllBytes(Paths.get("playlistplayer.txt"))).split("\n");
            FileWriter fw = new FileWriter(getFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            String newFile = "";
            ArrayList<String> historyItems = getHistory(content);
            String playlist = "playlist " + name + ":\n";
            for (int i = historyItems.size() - 1; i >= 0; i--) {
                if (i == historyItems.size() - 1 - 10) break;
                playlist += historyItems.get(i) + "\n";
            }

            playlist += "---\n";
            newFile += playlist;
            for (String line : content) {
                newFile += line + "\n";
            }

            bw.write(newFile);
            bw.close();
            fw.close();

        } catch (IOException e) {

        }
    }

    public static void savePlaylist() {

    }

    ;
}
