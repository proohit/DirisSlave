package imageboards;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class UrlHandler {

    public static String getImage(String URL) {
        return parseJson(URL).get("message").getAsString();
    }

    public static JsonObject parseJson(String URL) {
        try {
            URLConnection request = openConnection(URL);
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            return root.getAsJsonObject();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonArray parseJsonArray(String URL) {
        try {
            java.net.URLConnection request = openConnection(URL);
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            return root.getAsJsonArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static java.net.URLConnection openConnection(String URL) throws IOException {
        java.net.URL url = new URL(URL);
        java.net.URLConnection request = url.openConnection();
        request.addRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        request.connect();
        return request;
    }

    public static JsonObject getRandomObject(JsonArray object) {
        Random random = new Random();
        int rand = random.nextInt(object.size());
        return (JsonObject) object.get(rand);
    }
}
