package bot.audioplayer.api;

import java.util.Arrays;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class SearchHandler extends SpotifyApi {
    private final String searchUrl = SpotifyUrlFactory.getSearchUrl();
    private final String QUERY_QUERY = "q";
    private final String QUERY_TYPE = "type";
    private final String QUERY_TYPE_TRACK = "track";
    private final String RESULT_TRACKS = "tracks";
    private final String RESULT_TRACKS_ITEMS = "items";

    public JSONObject getTrackByQuery(String... query) {
        String trimmedQueryString = trimQuery(query);
        JSONObject result = baseGetRequest(searchUrl).queryString(QUERY_QUERY, trimmedQueryString)
                .queryString(QUERY_TYPE, QUERY_TYPE_TRACK).asJson().getBody().getObject();
        JSONObject tracks = result.getJSONObject(RESULT_TRACKS);
        JSONArray trackItems = tracks.getJSONArray(RESULT_TRACKS_ITEMS);
        return trackItems.getJSONObject(0);
    }

    public JSONArray getTracksByQuery(String... query) {
        String trimmedQueryString = trimQuery(query);
        JSONObject result = baseGetRequest(searchUrl).queryString(QUERY_QUERY, trimmedQueryString)
                .queryString(QUERY_TYPE, QUERY_TYPE_TRACK).asJson().getBody().getObject();
        JSONObject tracks = result.getJSONObject(RESULT_TRACKS);
        JSONArray trackItems = tracks.getJSONArray(RESULT_TRACKS_ITEMS);
        return trackItems;
    }

    private String trimQuery(String[] query) {
        String fullQueryStringFromArray = Arrays.toString(query);
        String queryStringWithoutBrackets = fullQueryStringFromArray.replace("[", "").replace("]", "");
        String queryStringWithoutBracketsWithoutCommas = queryStringWithoutBrackets.replace(",", "");
        return queryStringWithoutBracketsWithoutCommas;
    }
}