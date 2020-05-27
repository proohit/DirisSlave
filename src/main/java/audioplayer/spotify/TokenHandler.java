package audioplayer.spotify;

import java.util.Date;

import api.SpotifyUrlFactory;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import main.ReadPropertyFile;

public class TokenHandler {
    private static TokenHandler instance;
    private final String TOKEN_PROPERTY = "access_token";
    private final String GRANT_TYPE = "grant_type";
    private final String GRANT_TYPE_VALUE = "client_credentials";
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final String EXPIRES_IN_PROPERTY = "expires_in";
    private String token;
    private Date retrievalDate;
    private int expiresIn;

    private TokenHandler() {
    }

    public static TokenHandler getInstance() {
        if (instance == null) {
            instance = new TokenHandler();
        }

        return instance;
    }

    public String refreshToken() {
        JSONObject fetchResult = fetch();
        this.setToken(fetchResult.getString(TOKEN_PROPERTY));
        this.setExpiresIn(fetchResult.getInt(EXPIRES_IN_PROPERTY));
        this.setRetrievalDate(new Date());
        return this.getToken();
    }

    public boolean isTokenStillValid() {
        boolean isValid = false;
        if (retrievalDate != null) {
            if (calculateTimeSinceRetrieval() / 1000 < expiresIn) {
                isValid = true;
            }
        }
        return isValid;
    }

    private long calculateTimeSinceRetrieval() {
        return new Date().getTime() - this.getRetrievalDate().getTime();
    }

    private JSONObject fetch() {
        String spotifyClientId = ReadPropertyFile.getInstance().getSpotifyClientId();
        String spotifyClientSecret = ReadPropertyFile.getInstance().getSpotifyClientSecret();
        HttpResponse<JsonNode> response = Unirest.post(SpotifyUrlFactory.getTokenUrl())
                .basicAuth(spotifyClientId, spotifyClientSecret).header("Content-Type", CONTENT_TYPE)
                .field(GRANT_TYPE, GRANT_TYPE_VALUE).asJson();
        return response.getBody().getObject();
    }

    public String getToken() {
        if (isTokenStillValid()) {
            return token;
        } else {
            return refreshToken();
        }
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getRetrievalDate() {
        return retrievalDate;
    }

    public void setRetrievalDate(Date retrievalDate) {
        this.retrievalDate = retrievalDate;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
