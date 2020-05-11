package audioplayer.spotify;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public abstract class SpotifyApi {
    TokenHandler tokenHandler = TokenHandler.getInstance();
    SpotifyUrlFactory spotifyUrlFactory = SpotifyUrlFactory.getInstance();

    /**
     * Adds authentication header to requests
     *
     * @param request Request to add authentication headers to
     * @return
     */
    private HttpRequest buildHeaders(HttpRequest request) {
        return request.header("Authorization", "Bearer " + tokenHandler.getToken());
    }

    /**
     * Gets a basic POST request with headers included
     * @param url
     * @return
     */
    protected GetRequest baseGetRequest(String url) {
        return (GetRequest) buildHeaders(Unirest.get(url));
    }

    /**
     * Gets a basic POST request with headers included
     * @param url
     * @return
     */
    protected HttpRequestWithBody basePostRequest(String url) {
        return (HttpRequestWithBody) buildHeaders(Unirest.post(url));
    }
}
