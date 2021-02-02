package audioplayer.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class TrackHandler extends SpotifyApi {
    private SearchHandler searchHandler = new SearchHandler();
    private static final String TRACK_ID_PROPERTY = "id";
    private static final String TRACK_NAME_PROPERTY = "name";
    private static final String ARTISTS_PROPERTY = "artists";
    private static final String ARTIST_NAME_PROPERTY = "name";

    TrackHandler() {

    }

    public static JSONArray getArtistsOfTrack(JSONObject track) {
        return track.getJSONArray(ARTISTS_PROPERTY);
    }

    public static String getNameOfTrack(JSONObject track) {
        return track.getString(TRACK_NAME_PROPERTY);
    }

    public static String getNameOfArtist(JSONObject artist) {
        return artist.getString(ARTIST_NAME_PROPERTY);
    }

    public String getTrackIdBySearchQuery(String... searchQuery) {
        JSONObject foundTrack = searchHandler.getTrackByQuery(searchQuery);
        return extractTrackId(foundTrack);
    }

    private String extractTrackId(JSONObject track) {
        return track.getString(TRACK_ID_PROPERTY);
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