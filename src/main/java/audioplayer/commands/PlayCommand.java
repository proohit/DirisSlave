package audioplayer.commands;

import java.util.Arrays;
import java.util.List;

import audioplayer.spotify.RecommendationHandler;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class PlayCommand extends Command {
    public PlayCommand() {
        setCommand(prefix + "pl");
        setPermission("everyone");
        setTopic("music");
        setDescription("Play music by adding a search-term or by adding a link");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length <= 1) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        if (argStrings.length >= 2) {
            if (argStrings[1].equals("recommended")) {
                RecommendationHandler recommendationHandler = new RecommendationHandler();
                String[] searchQuery = extractSearchQueryFromArguments(argStrings);
                JSONArray recommendedTracksJson = recommendationHandler
                        .getRecommendationsByTrackSearchQuery(searchQuery);
                List<JSONObject> recommendedTracks = recommendedTracksJson.toList();
                if (recommendedTracks.size() == 0) {
                    Commands.sendBeautifulMessage(event, "No recommendations found...");
                    return;
                }
                recommendedTracks.forEach(track -> System.out.println(track.get("name")));
                recommendedTracks = recommendedTracks.subList(0, 2);
                recommendedTracks.forEach(recommendedTrackObject -> {
                    String firstArtist = recommendedTrackObject.getJSONArray("artists").getJSONObject(0)
                            .getString("name");
                    String trackName = recommendedTrackObject.getString("name");
                    Commands.player.loadAndPlay(event, "ytsearch: " + firstArtist + " " + trackName);
                });
            } else {
                String trackUrl = "";
                if (!(argStrings[1].contains("http") || argStrings[1].contains("https"))) {
                    trackUrl = "ytsearch: ";
                    for (int i = 1; i < argStrings.length; i++) {
                        trackUrl += argStrings[i] + " ";
                    }
                } else {
                    trackUrl = argStrings[1];
                }

                Commands.player.loadAndPlay(event, trackUrl);
            }
        }
    }

    private String[] extractSearchQueryFromArguments(String[] args) {
        return Arrays.copyOfRange(args, 2, args.length);
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<search term>");

        return help.toString();
    }
}
