package audioplayer.spotify;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.Arrays;

public class RecommendationHandler extends SpotifyApi {
    private final String TRACKS_QUERY = "seed_tracks";
    private final String recommendationsUrl = spotifyUrlFactory.getRecommendationUrl();
    private TrackHandler trackHandler = new TrackHandler();

    public RecommendationHandler() {

    }

    public JSONArray getRecommendationsByTrack(String trackId) {
        return this.baseGetRequest(recommendationsUrl).queryString(TRACKS_QUERY, trackId).asJson().getBody().getArray();
    }

    public JSONArray getRecommendationsByTrackSearchQuery(String... searchQuery) {
        String trackId = trackHandler.getTrackIdBySearchQuery(searchQuery);
        JSONArray recommendations = getRecommendationsByTrackIds(trackId);
        return recommendations;
    }

    private JSONArray getRecommendationsByTrackIds(String... trackIds) {
        String seedsString = trimSeedArray(trackIds);
        JSONObject fetchResult = this.baseGetRequest(recommendationsUrl).queryString(TRACKS_QUERY, seedsString).asJson()
                .getBody().getObject();
        JSONArray recommendedTracks = fetchResult.getJSONArray("tracks");
        return recommendedTracks;
    }

    private String trimSeedArray(String[] seedArray) {
        String fullSeedStringFromArray = Arrays.toString(seedArray);
        String seedStringWithoutBrackets = fullSeedStringFromArray.replace("[", "").replace("]", "");
        return seedStringWithoutBrackets.replace(" ", "");
    }
}
