package audioplayer.api;

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

    // TODO trim implementieren
    public JSONArray trimTracks(JSONArray tracks) {
        JSONArray trimmedArray = new JSONArray();
        for (int tracksArrayPosition = 0; tracksArrayPosition < tracks.length(); tracksArrayPosition++) {
            JSONObject object = tracks.getJSONObject(tracksArrayPosition);
        }
        return trimmedArray;
    }
}
