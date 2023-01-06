package bot.imageboards.api;

public class DanbooruUrlFactory {
    static final String API_URL = "https://danbooru.donmai.us/";

    public static String getPostsUrl() {
        return API_URL + "posts.json/";
    }

    public static String getTagsUrl() {
        return API_URL + "tags.json/";
    }
}