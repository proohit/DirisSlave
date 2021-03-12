package imageboards.api;

import java.util.Random;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class PixabayHandler extends PixabayApi {

    private static final String QUERY_QUERY = "q";
    private static final String QUERY_PAGE = "page";
    private static final int DEFAULT_PER_PAGE = 20;
    private static final String RESPONSE_HITS = "hits";
    private static final String RESPONSE_HITCOUNTAPI = "totalHits";
    private static final String HIT_LARGEIMAGEURL = "largeImageURL";
    private Random rnd = new Random();

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
        return response.getJSONArray(RESPONSE_HITS);
    }

    public JSONArray getHitsByQueryByPage(String query, int page) {
        JSONObject response = getResponseByQueryByPage(query, page);
        return response.getJSONArray(RESPONSE_HITS);
    }

    public String getImageUrlByHit(JSONObject hit) {
        return hit.getString(HIT_LARGEIMAGEURL);
    }

    public String getRandomImageUrlByQuery(String query) {
        JSONObject randomHit = getRandomHitByQuery(query);
        return randomHit.getString(HIT_LARGEIMAGEURL);
    }

    public JSONObject getRandomHit(JSONArray hits) {
        int randomIndex = rnd.nextInt(DEFAULT_PER_PAGE);
        return hits.getJSONObject(randomIndex);
    }

    public JSONObject getRandomHitByQuery(String query) {
        JSONObject standardHits = getResponseByQuery(query);
        int totalHitsByQuery = standardHits.getInt(RESPONSE_HITCOUNTAPI);
        int totalPages = totalHitsByQuery / DEFAULT_PER_PAGE;
        int randomPage = rnd.nextInt(totalPages);
        JSONArray randomHits = getHitsByQueryByPage(query, randomPage);
        return getRandomHit(randomHits);
    }
}