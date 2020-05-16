package audioplayer.commands.play;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import audioplayer.spotify.RecommendationHandler;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class PlayRecommendedCommand extends Command {
    public PlayRecommendedCommand() {
        setCommand("recommended");
        setTopic("music");
        setDescription("Play music by adding a search-term or by adding a link");
        setHelpString("");
        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length <= 0) {
            Commands.sendBeautifulMessage(event, getHelp());
        }
        RecommendationHandler recommendationHandler = new RecommendationHandler();
        String[] searchQuery = extractSearchQueryFromArguments(argStrings);
        JSONArray recommendedTracksJson = recommendationHandler.getRecommendationsByTrackSearchQuery(searchQuery);
        List<JSONObject> recommendedTracks = castList(JSONObject.class, recommendedTracksJson.toList());
        if (recommendedTracks.size() == 0) {
            Commands.sendBeautifulMessage(event, "No recommendations found...");
            return;
        }
        recommendedTracks.forEach(track -> System.out.println(track.get("name")));
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

    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for (Object o : c)
            r.add(clazz.cast(o));
        return r;
    }
}