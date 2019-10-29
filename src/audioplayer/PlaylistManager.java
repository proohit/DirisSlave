package audioplayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PlaylistManager {
    public static class Song {
        String title;
        String url;

        Song(String title, String url) {
            this.title = title;
            this.url = url;
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

    public static Song removeFromPlaylist(String playlist, int index) {
        Song toBeDeletedSong = null;
        try {
            ArrayList<Song> songs = getSongsOfPlaylist(playlist);
            if(songs.size() == 0 || index > songs.size() || index < 0) return null;
            for(int i = 1; i<=songs.size();i++) {
                if(i == index) {
                    toBeDeletedSong = songs.get(i-1);
                }
            }
            String[] content = new String(Files.readAllBytes(Paths.get("playlistplayer.txt"))).split("\n");

            FileWriter fw = new FileWriter(getFile());
            BufferedWriter bw = new BufferedWriter(fw);

            boolean hasFoundPlaylist = false;
            String newFile = "";

            for (String line : content) {
                if (line.matches("playlist " + playlist + ":")) {
                    newFile += line + "\n";
                    hasFoundPlaylist = true;
                } else if (line.contains("---") && hasFoundPlaylist) {
                    newFile += line + "\n";
                    hasFoundPlaylist = false;
                } else if (hasFoundPlaylist) {
                    if(toBeDeletedSong.title.equals(line.split("-/-")[0])) {
                    } else {
                        newFile += line+"\n";
                    }
                } else {
                    newFile+=line+"\n";
                }
            }
            bw.write(newFile);
            bw.close();
            fw.close();
        } catch (IOException e) {

        }
        return toBeDeletedSong;
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
                    playlists.add(new Song(title, url));
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

    public static boolean createPlaylist(String playlist) {
        boolean success = false;
        try {
            if ((getPlaylists().stream().filter(pl -> playlist.equals(pl))).count() == 1) return success;
            String[] content = new String(Files.readAllBytes(Paths.get("playlistplayer.txt"))).split("\n");
            FileWriter fw = new FileWriter(getFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuilder newFile = new StringBuilder();
            newFile.append("playlist " + playlist + ":\n---\n");
            for (String line : content) {
                newFile.append(line + "\n");
            }
            bw.write(newFile.toString());
            bw.close();
            fw.close();
            success = true;
        } catch (IOException e) {

        }
        return success;
    }
}
