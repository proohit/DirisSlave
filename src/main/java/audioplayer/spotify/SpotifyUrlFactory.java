package audioplayer.spotify;

public class SpotifyUrlFactory {
    private static SpotifyUrlFactory instance;
    private final String API_URL = "https://api.spotify.com/v1/";
    private final String ACCOUNTS_URL = "https://accounts.spotify.com/api/";

    private SpotifyUrlFactory() {
    }

    public static SpotifyUrlFactory getInstance() {
        if (instance == null)
            instance = new SpotifyUrlFactory();

        return instance;
    }

    // Playlist

    public String getPlaylistUrl() {
        return API_URL + "playlists/";
    }

    public String getPlaylistUrl(String playlistId) {
        return getPlaylistUrl() + playlistId + "/";
    }

    public String getPlaylistTracksUrl(String playlistId) {
        return getPlaylistUrl(playlistId) + "tracks/";
    }

    // Search
    public String getSearchUrl() {
        return API_URL + "search/";
    }

    // Token
    public String getTokenUrl() {
        return ACCOUNTS_URL + "token/";
    }

    // Track
    public String getTrackUrl(String trackId) {
        return API_URL + "tracks/" + trackId;
    }

    // Recommendations
    public String getRecommendationUrl() {
        return API_URL + "recommendations/";
    }

}
