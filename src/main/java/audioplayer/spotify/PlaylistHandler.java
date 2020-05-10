package audioplayer.spotify;

import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class PlaylistHandler extends SpotifyApi {
    private final String ITEMS_PROPERTY = "items";

    public PlaylistHandler() {

    }

    public JSONArray getTracksOfPlaylist(String playlistId) {
        JsonNode response = this.baseGetRequest(spotifyUrlFactory.getPlaylistTracksUrl(playlistId)).asJson().getBody();
        return response.getObject().getJSONArray(ITEMS_PROPERTY);
    }

    public JSONObject getPlaylist(String playlistId) {
        JsonNode response = this.baseGetRequest(spotifyUrlFactory.getPlaylistUrl(playlistId)).asJson().getBody();
        return response.getObject();
    }

    public String getPlaylistIdBySpotifyUri(String spotifyUri) {
        return spotifyUri.split("spotify:playlist:")[1];
    }

}
