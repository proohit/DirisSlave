package youtubewatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class YoutubeRSS {
	String rss = "";
	ArrayList<String> titles;
	ArrayList<String> videoUrls;
	ArrayList<String> dates;
	String channelid;

	public YoutubeRSS(String channelid) {
		try {
			this.channelid = channelid;
			URL urlString = new URL("https://www.youtube.com/feeds/videos.xml?channel_id=" + channelid);
			BufferedReader br = new BufferedReader(new InputStreamReader(urlString.openStream()));
			String lineString;
			while ((lineString = br.readLine()) != null) {
				rss += lineString;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRSS() {
		return rss;
	}

	public ArrayList<String> getTitles() {
		if (titles != null)
			return titles;
		titles = new ArrayList<String>();

		String tempUrl = rss;
		String startString = "<title>";
		String endString = "</title>";
		while (tempUrl.contains(startString)) {
			titles.add(
					tempUrl.substring(tempUrl.indexOf(startString) + startString.length(), tempUrl.indexOf(endString)));
			tempUrl = tempUrl.substring(tempUrl.indexOf(endString) + endString.length());
		}
		return titles;
	}

	public ArrayList<String> getContent(String startString, String endString) {
		ArrayList<String> templist = new ArrayList<String>();

		String tempUrl = rss;
		while (tempUrl.contains(startString)) {
			templist.add(
					tempUrl.substring(tempUrl.indexOf(startString) + startString.length(), tempUrl.indexOf(endString)));
			tempUrl = tempUrl.substring(tempUrl.indexOf(endString) + endString.length());
		}
		return templist;
	}

	public ArrayList<String> getVideoUrls() {
		if (videoUrls != null)
			return videoUrls;
		videoUrls = new ArrayList<String>();

		String tempUrl = rss;
		String startString = "<link rel=\"alternate\" href=\"";
		String endString = "<author>";
		while (tempUrl.contains(startString)) {
			videoUrls.add(tempUrl.substring(tempUrl.indexOf(startString) + startString.length(),
					tempUrl.indexOf(endString) - 5));
			tempUrl = tempUrl.substring(tempUrl.indexOf(endString) + endString.length());
		}
		return videoUrls;
	}

	public ArrayList<String> getPublishDates() {
		if (dates != null)
			return dates;
		dates = new ArrayList<String>();

		String tempUrl = rss;
		String startString = "<published>";
		String endString = "</published>";
		while (tempUrl.contains(startString)) {
			dates.add(
					tempUrl.substring(tempUrl.indexOf(startString) + startString.length(), tempUrl.indexOf(endString)));
			tempUrl = tempUrl.substring(tempUrl.indexOf(endString) + endString.length());
		}
		return dates;
	}

	public void updateRSS() {
		try {
			rss = "";
			URL urlString = new URL("https://www.youtube.com/feeds/videos.xml?channel_id=" + channelid);
			BufferedReader br = new BufferedReader(new InputStreamReader(urlString.openStream()));
			String lineString;
			while ((lineString = br.readLine()) != null) {
				rss += lineString;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getLatestVideo() {
		updateRSS();
		String tempUrl = rss;
		String startString = "<link rel=\"alternate\" href=\"";
		String endString = "<author>";
		tempUrl = tempUrl.substring(tempUrl.indexOf(endString) + endString.length());
		tempUrl = tempUrl.substring(tempUrl.indexOf(startString) + startString.length(),
				tempUrl.indexOf(endString) - 5);
		return tempUrl;

	}
}
