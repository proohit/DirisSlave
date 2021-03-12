package audioplayer.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class RecommendationHandler extends SpotifyApi {
    private static final String TRACKS_QUERY = "seed_tracks";
    private static final String GENRES_QUERY = "seed_genres";
    private static final String AVAILABLE_GENRE_SEEDS_RESPONSE_GENRES = "genres";
    private static final String RECOMMENDATIONS_RESPONSE_TRACKS = "tracks";
    private final String recommendationsUrl = SpotifyUrlFactory.getRecommendationUrl();
    private final String availableGenreSeedsUrl = SpotifyUrlFactory.getAvailableGenreSeedsUrl();
    private TrackHandler trackHandler = new TrackHandler();

    public JSONArray getRecommendationsByTrack(String trackId) {
        return this.baseGetRequest(recommendationsUrl).queryString(TRACKS_QUERY, trackId).asJson().getBody().getArray();
    }

    public JSONArray getRecommendationsByTrackSearchQuery(String... searchQuery) {
        String trackId = trackHandler.getTrackIdBySearchQuery(searchQuery);
        return getRecommendationsByTrackIds(trackId);
    }

    private JSONArray getRecommendationsByTrackIds(String... trackIds) {
        String seedsString = trimSeedArray(trackIds);
        JSONObject fetchResult = this.baseGetRequest(recommendationsUrl).queryString(TRACKS_QUERY, seedsString).asJson()
                .getBody().getObject();
        return fetchResult.getJSONArray(RECOMMENDATIONS_RESPONSE_TRACKS);
    }

    private String trimSeedArray(String[] seedArray) {
        String fullSeedStringFromArray = Arrays.toString(seedArray);
        String seedStringWithoutBrackets = fullSeedStringFromArray.replace("[", "").replace("]", "");
        return seedStringWithoutBrackets.replace(" ", "");
    }

    public List<String> getAvailableGenreSeeds() {
        JSONObject response = this.baseGetRequest(availableGenreSeedsUrl).asJson().getBody().getObject();
        JSONArray genresFromResponse = response.getJSONArray(AVAILABLE_GENRE_SEEDS_RESPONSE_GENRES);
        List<String> genres = new ArrayList<>();
        genresFromResponse.forEach(genreObject -> {
            String genre = (String) genreObject;
            genres.add(genre);
        });
        return genres;
    }

    public JSONArray getRecommendationsByGenre(String genre) {
        JSONObject response = this.baseGetRequest(recommendationsUrl).queryString(GENRES_QUERY, genre).asJson()
                .getBody().getObject();
        return response.getJSONArray(RECOMMENDATIONS_RESPONSE_TRACKS);
    }
}
