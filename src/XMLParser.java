import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class XMLParser {
	protected String url;
	protected String file = "";

	public XMLParser() {
		// TODO Auto-generated constructor stub
	}

	XMLParser(String url) {
		this.url = url;
		try {
			URL stringUrl = new URL(this.url);
			BufferedReader br = new BufferedReader(new InputStreamReader(stringUrl.openStream()));
			String lineString = "";
			while ((lineString = br.readLine()) != null) {
				file += lineString;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public String getXML() {
		return file;
	}
//	public String[] getMember(String member) {
//		
//	}
}
