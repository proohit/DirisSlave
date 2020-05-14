package api;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public class DanbooruApi implements BaseApi {

    @Override
    public GetRequest baseGetRequest(String url) {
        return Unirest.get(url);
    }

    @Override
    public HttpRequestWithBody basePostRequest(String url) {
        return Unirest.post(url);
    }

}