package api;

import audioplayer.spotify.TokenHandler;
import kong.unirest.GetRequest;
import kong.unirest.HttpRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public class SpotifyApi extends BasicApi {
    protected TokenHandler tokenHandler = TokenHandler.getInstance();

    /**
     * Adds authentication header to requests
     *
     * @param request Request to add authentication headers to
     * @return
     */
    private HttpRequestWithBody buildHeaders(HttpRequestWithBody request) {
        return request.header("Authorization", "Bearer " + tokenHandler.getToken());
    }

    private GetRequest buildHeaders(HttpRequest<GetRequest> request) {
        return request.header("Authorization", "Bearer " + tokenHandler.getToken());
    }

    @Override
    public GetRequest baseGetRequest(String url) {
        return buildHeaders(Unirest.get(url));
    }

    @Override
    public HttpRequestWithBody basePostRequest(String url) {
        return buildHeaders(Unirest.post(url));
    }
}
