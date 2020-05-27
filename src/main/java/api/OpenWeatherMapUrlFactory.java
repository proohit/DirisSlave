package api;

public class OpenWeatherMapUrlFactory {
    static final String API_URL = "http://api.openweathermap.org/data/2.5/";

    // Playlist

    public static String getForecastsUrl() {
        return API_URL + "forecast";
    }
}
