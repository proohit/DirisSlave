package api;

import audioplayer.spotify.TokenHandler;
import kong.unirest.GetRequest;
import kong.unirest.HttpRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public abstract class SpotifyApi implements BaseApi {
    protected TokenHandler tokenHandler = TokenHandler.getInstance();

    /**
     * Adds authentication header to requests
     *
     * @param request Request to add authentication headers to
     * @return
     */
    private HttpRequest buildHeaders(HttpRequest request) {
        return request.header("Authorization", "Bearer " + tokenHandler.getToken());
    }

    public GetRequest baseGetRequest(String url) {
        return (GetRequest) buildHeaders(Unirest.get(url));
    }

    public HttpRequestWithBody basePostRequest(String url) {
        return (HttpRequestWithBody) buildHeaders(Unirest.post(url));
    }
}
