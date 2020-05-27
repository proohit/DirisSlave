package api;

import kong.unirest.GetRequest;
import main.ReadPropertyFile;

public class PixabayApi extends BasicApi {
    private final String QUERY_KEY = "key";
    private final String KEY = ReadPropertyFile.getInstance().getPixabayKey();

    public GetRequest buildHeaders(GetRequest request) {
        return request.queryString(QUERY_KEY, KEY);
    }

    @Override
    public GetRequest baseGetRequest(String url) {
        return buildHeaders(super.baseGetRequest(url));
    }
}