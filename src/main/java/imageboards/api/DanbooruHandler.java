package imageboards.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import imageboards.exceptions.ImageNotFoundException;
import kong.unirest.GetRequest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import shared.api.BasicApi;

public class DanbooruHandler extends BasicApi {

    final String QUERY_LIMIT = "limit";
    final String QUERY_ENCODING = "utf8";
    final String QUERY_TAGS = "tags";
    final String QUERY_RANDOM = "random";
    final String POST_FILE_URL = "file_url";
    final String QUERY_TAGS_SEARCH_HIDE_EMPTY = "search[hide_empty]";
    final String QUERY_TAGS_SEARCH_ORDER = "search[order]";
    final String QUERY_TAGS_SEARCH_NAME_MATCHES = "search[name_matches]";
    final String TAG_NAME = "name";

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
        String imageUrl = getImageUrlOfPost(randomPost);
        return imageUrl;
    }

    public String getRandomImageByQuery(String tag1, String tag2) throws ImageNotFoundException {
        JSONArray response = basicImageByQueryRequest(tag1, tag2).queryString(QUERY_LIMIT, "1")
                .queryString(QUERY_RANDOM, true).asJson().getBody().getArray();
        if (response.length() < 1) {
            throw new ImageNotFoundException();
        }
        JSONObject randomPost = response.getJSONObject(0);
        String imageUrl = getImageUrlOfPost(randomPost);
        return imageUrl;
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
        JSONArray response = this.baseGetRequest(DanbooruUrlFactory.getTagsUrl()).queryString(queryStrings).asJson()
                .getBody().getArray();
        return response;
    }
}