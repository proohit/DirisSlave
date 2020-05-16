package api;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public class BasicApi {
    /**
     * 
     */
    public GetRequest baseGetRequest(String url) {
        return Unirest.get(url);
    }

    /**
     * Gets a basic POST request with headers included
     * 
     * @param url
     * @return
     */
    public HttpRequestWithBody basePostRequest(String url) {
        return Unirest.post(url);
    }
}