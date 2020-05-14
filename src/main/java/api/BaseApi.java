package api;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;

public interface BaseApi {
    /**
     * 
     */
    public GetRequest baseGetRequest(String url);

    /**
     * Gets a basic POST request with headers included
     * 
     * @param url
     * @return
     */
    public HttpRequestWithBody basePostRequest(String url);

}