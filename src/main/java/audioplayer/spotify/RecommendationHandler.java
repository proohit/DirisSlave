package audioplayer.spotify;

import kong.unirest.json.JSONArray;

import java.util.Arrays;
import java.util.logging.Logger;

public class RecommendationHandler extends SpotifyApi {
    private static Logger LOGGER;

    static {
        String path = RecommendationHandler.class.getClassLoader()
                .getResource("logging.properties")
                .getFile();
        System.setProperty("java.util.logging.config.file", path);
        LOGGER = Logger.getLogger(RecommendationHandler.class.getName());
    }

    private final String TRACKS_QUERY = "seed_tracks";
    private final String recommendationsUrl = spotifyUrlFactory.getRecommendationUrl();

    public RecommendationHandler() {

    }

    public JSONArray getRecommendationsByTrack(String trackId) {
        return this.baseGetRequest(recommendationsUrl).queryString(TRACKS_QUERY, trackId).asJson().getBody().getArray();
    }

    public JSONArray getRecommendationsByTracks(String... seeds) {
        String seedsString = trimSeedArray(seeds);
        LOGGER.fine(this.getClass().getName() + ": Fetching recommendations based on: " + seedsString);
        return this.baseGetRequest(recommendationsUrl).queryString(TRACKS_QUERY, seedsString).asJson().getBody().getArray();
    }

    private String trimSeedArray(String[] seedArray) {
        String fullSeedStringFromArray = Arrays.toString(seedArray);
        String seedStringWithoutBrackets = fullSeedStringFromArray.replace("[", "").replace("]", "");
        return seedStringWithoutBrackets.replace(" ", "");
    }
}
