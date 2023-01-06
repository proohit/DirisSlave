package bot.weather.api;

import bot.main.ReadPropertyFile;
import bot.shared.api.BasicApi;
import kong.unirest.GetRequest;

public class OpenWeatherMapApi extends BasicApi {

    private final String QUERY_APPID_VALUE = ReadPropertyFile.getInstance().getOpenWeatherMapsAppId();
    private final String QUERY_APPID = "appid";

    private GetRequest buildHeaders(GetRequest request) {
        return request.queryString(QUERY_APPID, QUERY_APPID_VALUE);
    }

    @Override
    public GetRequest baseGetRequest(String url) {
        return buildHeaders(super.baseGetRequest(url));
    }

}