package youtubewatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class YoutubeXML extends XMLParser {
	private String channelid = "";
	YoutubeRSS rss;

	YoutubeXML(String url) {
		this.url = url;
		try {
			URL stringUrl = new URL(url);
			BufferedReader br;

			if (url.contains("youtube.com/channel/")) {
				channelid = url.substring(url.indexOf("youtube.com/channel/") + 20);
			} else {
				br = new BufferedReader(new InputStreamReader(stringUrl.openStream()));
				String lineString = "";
				while ((lineString = br.readLine()) != null) {
					if (lineString.contains("youtube.com/channel/")) {
						int pos1 = lineString.indexOf("youtube.com/channel/");
						int pos2 = lineString.indexOf("><link rel=\"alternate\"");
						channelid = lineString.substring(pos1 + 20, pos2 - 1);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getChannelName() {
		return getRSS().getTitles().get(0);
	}
	public String getStringRSS() {
		if (rss == null)
			rss = new YoutubeRSS(channelid);
		return rss.getRSS();
	}
	public String getUrl() {
		return url;
	}
	public YoutubeRSS getRSS() {
		if (rss == null)
			rss = new YoutubeRSS(channelid);
		return rss;
	}

	public ArrayList<String> getTitles() {
		if(rss == null) {
			rss = new YoutubeRSS(channelid);
		}
		return rss.getTitles();
	}

}
