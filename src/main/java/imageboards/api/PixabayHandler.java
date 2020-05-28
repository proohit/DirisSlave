package imageboards.api;

import java.util.Random;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class PixabayHandler extends PixabayApi {

    private final String QUERY_QUERY = "q";
    private final String QUERY_PAGE = "page";
    private final int DEFAULT_PER_PAGE = 20;
    private final String RESPONSE_HITS = "hits";
    private final String RESPONSE_HITCOUNT = "total";
    private final String HIT_LARGEIMAGEURL = "largeImageURL";

    public JSONObject getResponseByQuery(String query) {
        return this.baseGetRequest(PixabayApiFactory.getBaseUrl()).queryString(QUERY_QUERY, query).asJson().getBody()
                .getObject();
    }

    public JSONObject getResponseByQueryByPage(String query, int page) {
        return this.baseGetRequest(PixabayApiFactory.getBaseUrl()).queryString(QUERY_QUERY, query)
                .queryString(QUERY_PAGE, page).asJson().getBody().getObject();
    }

    public JSONArray getHitsByQuery(String query) {
        JSONObject response = getResponseByQuery(query);
        JSONArray hits = response.getJSONArray(RESPONSE_HITS);
        return hits;
    }

    public JSONArray getHitsByQueryByPage(String query, int page) {
        JSONObject response = getResponseByQueryByPage(query, page);
        JSONArray hits = response.getJSONArray(RESPONSE_HITS);
        return hits;
    }

    public String getImageUrlByHit(JSONObject hit) {
        return hit.getString(HIT_LARGEIMAGEURL);
    }

    public String getRandomImageUrlByQuery(String query) {
        JSONObject randomHit = getRandomHitByQuery(query);
        String largeImageUrl = randomHit.getString(HIT_LARGEIMAGEURL);
        return largeImageUrl;
    }

    public JSONObject getRandomHit(JSONArray hits) {
        Random random = new Random();
        int randomIndex = random.nextInt(DEFAULT_PER_PAGE);
        return hits.getJSONObject(randomIndex);
    }

    public JSONObject getRandomHitByQuery(String query) {
        JSONObject standardHits = getResponseByQuery(query);
        int totalHitsByQuery = standardHits.getInt(RESPONSE_HITCOUNT);
        int totalPages = totalHitsByQuery / DEFAULT_PER_PAGE;
        Random random = new Random();
        int randomPage = random.nextInt(totalPages);
        JSONArray randomHits = getHitsByQueryByPage(query, randomPage);
        JSONObject randomHit = getRandomHit(randomHits);
        return randomHit;
    }
}