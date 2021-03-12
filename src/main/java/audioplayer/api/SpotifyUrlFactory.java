package audioplayer.api;

public class SpotifyUrlFactory {
    static final String API_URL = "https://api.spotify.com/v1/";
    static final String ACCOUNTS_URL = "https://accounts.spotify.com/api/";

    private SpotifyUrlFactory() {
    }
    // Playlist

    public static String getPlaylistUrl() {
        return API_URL + "playlists/";
    }

    public static String getPlaylistUrl(String playlistId) {
        return getPlaylistUrl() + playlistId + "/";
    }

    public static String getPlaylistTracksUrl(String playlistId) {
        return getPlaylistUrl(playlistId) + "tracks/";
    }

    // Search
    public static String getSearchUrl() {
        return API_URL + "search/";
    }

    // Token
    public static String getTokenUrl() {
        return ACCOUNTS_URL + "token/";
    }

    // Track
    public static String getTrackUrl(String trackId) {
        return API_URL + "tracks/" + trackId;
    }

    // Recommendations
    public static String getRecommendationUrl() {
        return API_URL + "recommendations/";
    }

    public static String getAvailableGenreSeedsUrl() {
        return getRecommendationUrl() + "available-genre-seeds/";
    }

}
