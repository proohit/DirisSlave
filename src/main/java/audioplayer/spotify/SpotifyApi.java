package audioplayer.spotify;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public abstract class SpotifyApi {
    TokenHandler tokenHandler = TokenHandler.getInstance();
    SpotifyUrlFactory spotifyUrlFactory = SpotifyUrlFactory.getInstance();

    private HttpRequest buildHeaders(HttpRequest request) {
        return request.header("Authorization", "Bearer " + tokenHandler.getToken());
    }

    protected GetRequest baseGetRequest(String url) {
        return (GetRequest) buildHeaders(Unirest.get(url));
    }

    protected HttpRequestWithBody basePostRequest(String url) {
        return (HttpRequestWithBody) buildHeaders(Unirest.post(url));
    }
}
