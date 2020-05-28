package imageboards.api;

import kong.unirest.json.JSONObject;
import shared.api.BasicApi;

public class NekobotHandler extends BasicApi {

    final String QUERY_TYPE = "type";
    final String QUERY_TYPE_COFFEE = "coffee";
    final String QUERY_TYPE_GAH = "gah";
    final String QUERY_TYPE_THIGH = "thigh";
    final String RESPONSE_IMAGEURL = "message";

    public String getCoffeImage() {
        JSONObject response = this.baseGetRequest(NekobotUrlFactory.getImagetUrl())
                .queryString(QUERY_TYPE, QUERY_TYPE_COFFEE).asJson().getBody().getObject();
        String imageUrl = getImageUrl(response);
        return imageUrl;
    }

    public String getGahImage() {
        JSONObject response = this.baseGetRequest(NekobotUrlFactory.getImagetUrl())
                .queryString(QUERY_TYPE, QUERY_TYPE_GAH).asJson().getBody().getObject();
        String imageUrl = getImageUrl(response);
        return imageUrl;
    }

    public String getThighImage() {
        JSONObject response = this.baseGetRequest(NekobotUrlFactory.getImagetUrl())
                .queryString(QUERY_TYPE, QUERY_TYPE_THIGH).asJson().getBody().getObject();
        String imageUrl = getImageUrl(response);
        return imageUrl;
    }

    private String getImageUrl(JSONObject response) {
        return response.getString(RESPONSE_IMAGEURL);
    }

}