package audioplayer.spotify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import api.SpotifyApi;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class TrackHandler extends SpotifyApi {
    private SearchHandler searchHandler = new SearchHandler();
    private final String TRACK_ID = "id";

    TrackHandler() {

    }

    public String getTrackIdBySearchQuery(String... searchQuery) {
        JSONObject foundTrack = searchHandler.getTrackByQuery(searchQuery);
        String trackId = extractTrackId(foundTrack);
        return trackId;
    }

    private String extractTrackId(JSONObject track) {
        String id = track.getString(TRACK_ID);
        return id;
    }

    private String[] extractTrackIds(JSONArray tracks) {
        List<String> ids = new ArrayList<>();

        tracks.forEach(trackObject -> {
            JSONObject track = (JSONObject) trackObject;
            String id = track.getString(TRACK_ID);
            ids.add(id);
        });

        return ids.toArray(new String[0]);
    }

    public String[] extractTrackIdsFromUri(String... uris) {
        List<String> ids = new ArrayList<>();
        Arrays.asList(uris).forEach(uri -> {
            try {
                ids.add(extractTrackIdFromUri(uri));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        return ids.toArray(new String[0]);
    }

    public String extractTrackIdFromUri(String uri) throws InvalidUriException {
        if (uri.contains("spotify") || uri.contains("tracks")) {
            return uri.split("spotify:track:")[1];
        } else {
            throw new InvalidUriException();
        }
    }
}