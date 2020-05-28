package imageboards.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import imageboards.exceptions.ImageNotFoundException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import shared.api.BasicApi;

public class DanbooruHandler extends BasicApi {

    final String QUERY_LIMIT = "limit";
    final String QUERY_ENCODING = "utf8";
    final String QUERY_TAGS = "tags";
    final String POST_FILE_URL = "file_url";
    final String QUERY_TAGS_SEARCH_HIDE_EMPTY = "search[hide_empty]";
    final String QUERY_TAGS_SEARCH_ORDER = "search[order]";
    final String QUERY_TAGS_SEARCH_NAME_MATCHES = "search[name_matches]";
    final String TAG_NAME = "name";

    public String getImageByQuery(String tag) throws ImageNotFoundException {
        JSONArray response = this.baseGetRequest(DanbooruUrlFactory.getPostsUrl())
                .queryString(QUERY_ENCODING, "%E2%9C%93").queryString(QUERY_LIMIT, "200").queryString(QUERY_TAGS, tag)
                .asJson().getBody().getArray();
        if (response.length() < 1) {
            throw new ImageNotFoundException();
        }
        JSONObject randomPostOfReponse = getRandomPost(response);
        String imageUrl = getImageUrlOfPost(randomPostOfReponse);
        return imageUrl;
    }

    public String getImageByQuery(String tag1, String tag2) throws ImageNotFoundException {
        JSONArray response = this.baseGetRequest(DanbooruUrlFactory.getPostsUrl())
                .queryString(QUERY_ENCODING, "%E2%9C%93").queryString(QUERY_LIMIT, "200")
                .queryString(QUERY_TAGS, new String[] { tag1, tag2 }).asJson().getBody().getArray();
        if (response.length() < 1) {
            throw new ImageNotFoundException();
        }
        JSONObject randomPostOfReponse = getRandomPost(response);
        String imageUrl = getImageUrlOfPost(randomPostOfReponse);
        return imageUrl;
    }

    private String getImageUrlOfPost(JSONObject post) {
        return post.getString(POST_FILE_URL);
    }

    private JSONObject getRandomPost(JSONArray posts) {
        Random random = new Random();
        int rand = random.nextInt(posts.length());
        return (JSONObject) posts.get(rand);
    }

    public JSONArray getTagsByQuery(String query) {
        Map<String, Object> queryStrings = new HashMap<>();
        queryStrings.put(QUERY_TAGS_SEARCH_HIDE_EMPTY, "true");
        queryStrings.put(QUERY_TAGS_SEARCH_ORDER, "count");
        queryStrings.put(QUERY_TAGS_SEARCH_NAME_MATCHES, "*" + query + "*");
        JSONArray response = this.baseGetRequest(DanbooruUrlFactory.getTagsUrl()).queryString(queryStrings).asJson()
                .getBody().getArray();
        return response;
    }
}