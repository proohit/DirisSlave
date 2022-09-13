package audioplayer.commands.play;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import audioplayer.api.RecommendationHandler;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import main.CommandManager;
import main.MessageUtils;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import shared.commands.SlashCommand;
import shared.util.ListUtilies;

public class PlayRecommendedCommand extends SlashCommand {
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
            MessageUtils.sendBeautifulMessage(event, "No recommendations found...");
            return;
        }
        recommendedTracks = recommendedTracks.subList(0, 2);
        recommendedTracks.forEach(recommendedTrackObject -> {
            String firstArtist = recommendedTrackObject.getJSONArray("artists").getJSONObject(0).getString("name");
            String trackName = recommendedTrackObject.getString("name");
            CommandManager.player.loadAndPlay(event, "ytsearch: " + firstArtist + " " + trackName);
        });
    }

    private String[] extractSearchQueryFromArguments(String[] args) {
        return Arrays.copyOfRange(args, 0, args.length);
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        RecommendationHandler recommendationHandler = new RecommendationHandler();
        OptionMapping searchQuery = event.getOption("search");
        if (searchQuery == null) {
            event.getHook().sendMessage("No search term given").queue();
            return;
        }
        JSONArray recommendedTracksJson = recommendationHandler
                .getRecommendationsByTrackSearchQuery(searchQuery.getAsString());
        List<JSONObject> recommendedTracks = ListUtilies.castList(JSONObject.class, recommendedTracksJson.toList());
        if (recommendedTracks.isEmpty()) {
            event.getHook().sendMessage("No recommendations found...").queue();
            return;
        }
        recommendedTracks = recommendedTracks.subList(0, 2);
        recommendedTracks.forEach(recommendedTrackObject -> {
            String firstArtist = recommendedTrackObject.getJSONArray("artists").getJSONObject(0).getString("name");
            String trackName = recommendedTrackObject.getString("name");
            CommandManager.player.loadAndPlay(event, "ytsearch: " + firstArtist + " " + trackName);
        });
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING, "search", "search term to play recommended songs of"));
    }

    @Override
    public List<String> getNames() {
        return List.of("recommended");
    }

    @Override
    public List<SlashCommand> getSlashSubCommands() {
        return Collections.emptyList();
    }

}