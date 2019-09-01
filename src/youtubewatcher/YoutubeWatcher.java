package youtubewatcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import main.Commands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

public class YoutubeWatcher {

	public static Map<YoutubeXML, String> latestvideos = new HashMap<YoutubeXML, String>();

	public static void start(JDA jda) {
		System.out.println("initializing youtube watcher..");
		initiate();
		System.out.println("youtube watcher initialized");
		Thread timer = new Thread() {
			public void run() {
				Timer time = new Timer();
				time.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						for (YoutubeXML currentObject : latestvideos.keySet()) {
							String currentVideo = currentObject.getRSS().getLatestVideo();
							if (!currentVideo.equals(latestvideos.get(currentObject))) {
								List<TextChannel> channels = jda.getTextChannelsByName("ytnotifications", true);
								Commands.sendMessage(channels.get(0), "New video found for Channel: "
										+ currentObject.getChannelName() + "\n" + currentVideo);
								latestvideos.replace(currentObject, latestvideos.get(currentObject), currentVideo);
							} else {

							}
						}
					}
				}, 5*60*1000, 5*60*1000);
			}
		};
		timer.start();

	}

	private static void initiate() {
		try {
			System.out.println("loading channels from file");
			FileReader fr = new FileReader("channels.txt");
			BufferedReader br = new BufferedReader(fr);
			String channel = "";
			while ((channel = br.readLine()) != null) {
				YoutubeXML yt = new YoutubeXML(channel);
				latestvideos.put(yt, yt.getRSS().getLatestVideo());
				System.out.println("loaded channel " + channel);
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void update(String channel) {

		YoutubeXML yt = new YoutubeXML(channel);
		latestvideos.put(yt, yt.getRSS().getLatestVideo());
		try {
			FileWriter fw = new FileWriter("channels.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(channel);
			bw.newLine();
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void remove(String channel) throws Exception {
		System.out.println("removing channel " + channel);
		for (YoutubeXML yt : latestvideos.keySet()) {
			if (channel.equals(yt.getChannelName())) {
				latestvideos.remove(yt);
				updateTextFile();
				System.out.println("channel removed");
				return;
			}
		}
		throw new Exception();

	}

	public static void updateTextFile() {
		try {
			FileWriter fw = new FileWriter("channels.txt", false);
			BufferedWriter bw = new BufferedWriter(fw);
			for (YoutubeXML yt : latestvideos.keySet()) {
				bw.write(yt.url);
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String status() {
		if (latestvideos.size() == 0) {
			return "no channels saved to watch.. add one by typing #yt <URL to channel>";
		}
		String list = "";
		for (YoutubeXML yt : latestvideos.keySet()) {
			list += yt.getTitles().get(0) + "\n";
		}
		return list;
	}
}
