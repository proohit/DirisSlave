package youtubewatcher;

import java.io.*;
import java.net.URL;

public class URLFetcher {

	static public String getContent(String url) throws IOException {
		URL urlObj = new URL(url);
		String content = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(urlObj.openStream()));
		String lineString;
		while ((lineString = br.readLine()) != null) {
			content += lineString;
		}
		return content;
	}

	static public String getSpecificContent(String url, String startString, String endString) throws IOException {
		URL urlObj = new URL(url);
		String content = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(urlObj.openStream()));
		FileWriter fw = new FileWriter("testfile.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		String lineString;
		boolean contains = false;
		while ((lineString = br.readLine()) != null) {
//			if (lineString.contains(startString))
//				contains = true;
//			else if(lineString.contains(endString))
//				contains = false;
//			if (contains == true)
//				content += lineString;
			bw.append(lineString + "\n");
		}
		bw.close();
		fw.close();
		br.close();
		return content;
	}
}
