package bot.audioplayer.api;

import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class PlaylistHandler extends SpotifyApi {
    private static final String ITEMS_PROPERTY = "items";

    public JSONArray getTracksOfPlaylist(String playlistId) {
        JsonNode response = this.baseGetRequest(SpotifyUrlFactory.getPlaylistTracksUrl(playlistId)).asJson().getBody();
        return response.getObject().getJSONArray(ITEMS_PROPERTY);
    }

    public JSONObject getPlaylist(String playlistId) {
        JsonNode response = this.baseGetRequest(SpotifyUrlFactory.getPlaylistUrl(playlistId)).asJson().getBody();
        return response.getObject();
    }
}
