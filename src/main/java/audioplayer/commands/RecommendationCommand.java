package audioplayer.commands;

import audioplayer.spotify.RecommendationHandler;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.Arrays;

public class RecommendationCommand extends Command {
    public RecommendationCommand() {
        setCommand(prefix + "recommendation");
        setPermission("everyone");
        setTopic("music");
        setDescription("Get Recommendations based on up to 5 Spotify Tracks");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length <= 1) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        if (argStrings.length >= 2) {
            RecommendationHandler recommendationHandler = new RecommendationHandler();
            String[] searchQuery = extractSearchQueryFromArguments(argStrings);
            JSONArray tracks = recommendationHandler.getRecommendationsByTrackSearchQuery(searchQuery);
            final StringBuilder recommendationsString = new StringBuilder();
            tracks.forEach(trackObject -> {
                JSONObject track = (JSONObject) trackObject;
                recommendationsString.append("\n").append("Song name: ").append(track.getString("name"))
                        .append(", uri: ").append(track.getString("uri")).append("\n");
            });
            main.Commands.sendMessage(event, recommendationsString.toString());
        }
    }

    private String[] extractSearchQueryFromArguments(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<spotify Track ids>");

        return help.toString();
    }
}
