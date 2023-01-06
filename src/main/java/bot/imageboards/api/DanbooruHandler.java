package bot.imageboards.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.imageboards.exceptions.ImageNotFoundException;
import bot.shared.api.BasicApi;
import kong.unirest.GetRequest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class DanbooruHandler extends BasicApi {

    private static final String QUERY_LIMIT = "limit";
    private static final String QUERY_ENCODING = "utf8";
    private static final String QUERY_TAGS = "tags";
    private static final String QUERY_RANDOM = "random";
    private static final String POST_FILE_URL = "file_url";
    private static final String QUERY_TAGS_SEARCH_HIDE_EMPTY = "search[hide_empty]";
    private static final String QUERY_TAGS_SEARCH_ORDER = "search[order]";
    private static final String QUERY_TAGS_SEARCH_NAME_MATCHES = "search[name_matches]";

    private GetRequest basicImageByQueryRequest(String tag) {
        return this.baseGetRequest(DanbooruUrlFactory.getPostsUrl()).queryString(QUERY_ENCODING, "%E2%9C%93")
                .queryString(QUERY_TAGS, tag);
    }

    private GetRequest basicImageByQueryRequest(String tag1, String tag2) {
        return this.baseGetRequest(DanbooruUrlFactory.getPostsUrl()).queryString(QUERY_ENCODING, "%E2%9C%93")
                .queryString(QUERY_TAGS, tag1 + " " + tag2);
    }

    public String getRandomImageByQuery(String tag) throws ImageNotFoundException {
        JSONArray response = basicImageByQueryRequest(tag).queryString(QUERY_LIMIT, 1).queryString(QUERY_RANDOM, true)
                .asJson().getBody().getArray();
        if (response.length() < 1) {
            throw new ImageNotFoundException();
        }
        JSONObject randomPost = response.getJSONObject(0);
        return getImageUrlOfPost(randomPost);
    }

    public String getRandomImageByQuery(String tag1, String tag2) throws ImageNotFoundException {
        JSONArray response = basicImageByQueryRequest(tag1, tag2).queryString(QUERY_LIMIT, "1")
                .queryString(QUERY_RANDOM, true).asJson().getBody().getArray();
        if (response.length() < 1) {
            throw new ImageNotFoundException();
        }
        JSONObject randomPost = response.getJSONObject(0);
        return getImageUrlOfPost(randomPost);
    }

    public List<String> getImageUrlsByQuery(String tag) throws ImageNotFoundException {
        JSONArray response = basicImageByQueryRequest(tag).queryString(QUERY_LIMIT, "200").asJson().getBody()
                .getArray();
        if (response.length() < 1) {
            throw new ImageNotFoundException();
        }
        List<String> imageUrls = new ArrayList<>();
        response.forEach(postObject -> {
            JSONObject post = (JSONObject) postObject;
            imageUrls.add(getImageUrlOfPost(post));
        });
        return imageUrls;
    }

    public List<String> getImageUrlsByQuery(String tag1, String tag2) throws ImageNotFoundException {
        JSONArray response = basicImageByQueryRequest(tag1, tag2).queryString(QUERY_LIMIT, "200").asJson().getBody()
                .getArray();
        if (response.length() < 1) {
            throw new ImageNotFoundException();
        }
        List<String> imageUrls = new ArrayList<>();
        response.forEach(postObject -> {
            JSONObject post = (JSONObject) postObject;
            imageUrls.add(getImageUrlOfPost(post));
        });
        return imageUrls;
    }

    private String getImageUrlOfPost(JSONObject post) {
        return post.getString(POST_FILE_URL);
    }

    public JSONArray getTagsByQuery(String query) {
        Map<String, Object> queryStrings = new HashMap<>();
        queryStrings.put(QUERY_TAGS_SEARCH_HIDE_EMPTY, "true");
        queryStrings.put(QUERY_TAGS_SEARCH_ORDER, "count");
        queryStrings.put(QUERY_TAGS_SEARCH_NAME_MATCHES, "*" + query + "*");
        return this.baseGetRequest(DanbooruUrlFactory.getTagsUrl()).queryString(queryStrings).asJson().getBody()
                .getArray();
    }
}