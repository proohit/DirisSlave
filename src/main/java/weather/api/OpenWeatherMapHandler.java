package weather.api;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class OpenWeatherMapHandler extends OpenWeatherMapApi {

    private final String QUERY_UNITS = "units";
    private final String QUERY_UNITS_METRIC = "metric";
    private final String QUERY_QUERY = "q";
    private final String RESPONSE_LIST = "list";
    private final String WEATHERDATA_TIME = "dt_txt";
    private final String WEATHERDATA_MAIN = "main";
    private final String WEATHERDATA_MAIN_TEMPERATURE = "temp";
    private final String WEATHERDATA_WEATHER = "weather";
    private final String WEATHERDATA_WEATHER_STATUS = "main";

    public JSONArray getWeatherDataOfCity(String cityQuery) {
        JSONObject response = this.baseGetRequest(OpenWeatherMapUrlFactory.getForecastsUrl())
                .queryString(QUERY_UNITS, QUERY_UNITS_METRIC).queryString(QUERY_QUERY, cityQuery).asJson().getBody()
                .getObject();
        JSONArray list = response.getJSONArray(RESPONSE_LIST);

        return list;
    }

    public String getTimeOfWeatherData(JSONObject weatherDataObject) {
        return weatherDataObject.getString(WEATHERDATA_TIME);
    }

    public Double getTemperatuteOfWeatherData(JSONObject weatherDataObject) {
        return weatherDataObject.getJSONObject(WEATHERDATA_MAIN).getDouble(WEATHERDATA_MAIN_TEMPERATURE);
    }

    public String getWeatherConditionOfWeatherData(JSONObject weatherDataObject) {
        return weatherDataObject.getJSONArray(WEATHERDATA_WEATHER).getJSONObject(0)
                .getString(WEATHERDATA_WEATHER_STATUS);
    }
}