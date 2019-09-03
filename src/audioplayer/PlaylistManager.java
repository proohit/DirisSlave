package audioplayer;

import java.io.*;
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
    };
    public static ArrayList<String> getHistory() {
        ArrayList<String> historyItems = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(getFile());
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("History:")) {
                    while(scanner.hasNextLine() && !(line=scanner.nextLine()).isEmpty()) {
                        historyItems.add(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return historyItems;
    };
    public static void saveHistory() {

    };
    public static void writeHistoryItem(String url) {
        try {
            FileWriter fw = new FileWriter(getFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(url+"\n");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadPlaylist() {

    };
    public static void savePlaylist() {

    };
}
