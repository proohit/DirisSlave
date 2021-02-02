package audioplayer.commands;

import java.util.Arrays;

import audioplayer.api.RecommendationHandler;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class RecommendationCommand extends Command {
    public RecommendationCommand() {
        addPermission("everyone");
    }

    private String[] extractSearchQueryFromArguments(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length <= 0) {
            main.Commands.sendMessage(event, getHelp());
            return;
        } else {
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

    @Override
    protected String[] defineCommand() {
        return new String[] { "recs", "recommendation" };
    }

    @Override
    protected String defineDescription() {
        return "Get recommendations based on a song query";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "<spotify song URI>";
    }
}
