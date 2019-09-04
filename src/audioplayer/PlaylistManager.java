package audioplayer;

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
                if(line.contains("History:")) {
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
            for(String line:content) {
                if(line.contains("playlist "+name)) {
                    foundPlaylist=true;
                    continue;
                }
                if(line.contains("---")) {
                    break;
                }
                if(foundPlaylist==true) {
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

    public static void savePlaylistFromHistory(String name) {
        try {
            String[] content = new String(Files.readAllBytes(Paths.get("playlistplayer.txt"))).split("\n");
            FileWriter fw = new FileWriter(getFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            String newFile = "";
            ArrayList<String> historyItems = getHistory(content);
            String playlist = "playlist " + name + ":\n";
            for(int i = historyItems.size()-1; i>=0;i--) {
                if(i == historyItems.size()-1-10) break;
                playlist+= historyItems.get(i) + "\n";
            }

            playlist+="---\n";
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
