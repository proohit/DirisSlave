package api;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public class BasicApi {
    /**
     * 
     */
    protected GetRequest baseGetRequest(String url) {
        return Unirest.get(url);
    }

    /**
     * Gets a basic POST request with headers included
     * 
     * @param url
     * @return
     */
    protected HttpRequestWithBody basePostRequest(String url) {
        return Unirest.post(url);
    }
}