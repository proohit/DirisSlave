package audioplayer.commands.play;

import java.util.Arrays;
import java.util.List;

import audioplayer.api.RecommendationHandler;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;
import shared.util.ListUtilies;

public class PlayRecommendedCommand extends Command {
    public PlayRecommendedCommand() {
        addPermission("everyone");
        addCommendPrefix("rec", "recommended");
        setDescription("Play music based on recommendations for provided search term");
        setTopic("music");
        setHelpString("<search term to search for on spotify>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        RecommendationHandler recommendationHandler = new RecommendationHandler();
        String[] searchQuery = extractSearchQueryFromArguments(argStrings);
        JSONArray recommendedTracksJson = recommendationHandler.getRecommendationsByTrackSearchQuery(searchQuery);
        List<JSONObject> recommendedTracks = ListUtilies.castList(JSONObject.class, recommendedTracksJson.toList());
        if (recommendedTracks.isEmpty()) {
            Commands.sendBeautifulMessage(event, "No recommendations found...");
            return;
        }
        recommendedTracks = recommendedTracks.subList(0, 2);
        recommendedTracks.forEach(recommendedTrackObject -> {
            String firstArtist = recommendedTrackObject.getJSONArray("artists").getJSONObject(0).getString("name");
            String trackName = recommendedTrackObject.getString("name");
            Commands.player.loadAndPlay(event, "ytsearch: " + firstArtist + " " + trackName);
        });
    }

    private String[] extractSearchQueryFromArguments(String[] args) {
        return Arrays.copyOfRange(args, 0, args.length);
    }

}