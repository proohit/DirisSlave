package bot.audioplayer.api;

public class Utilities {

    public static String getPlaylistIdBySpotifyUri(String spotifyUri) {
        return spotifyUri.split("spotify:playlist:")[1];
    }

    public static String getTrackIdBySpotifyUri(String spotifyUri) {
        return spotifyUri.split("spotify:track:")[1];
    }
}
