package bot.audioplayer.commands.play;

import java.util.List;

import org.simmetrics.StringDistance;
import org.simmetrics.metrics.StringDistances;

import bot.audioplayer.api.RecommendationHandler;
import bot.audioplayer.api.TrackHandler;
import bot.main.CommandManager;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import bot.shared.util.ListUtilies;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PlayRandomCommand extends Command {
    public PlayRandomCommand() {
        addPermission("everyone");
        addCommendPrefix("random", "genre");
        setDescription("plays random songs based on genre");
        setTopic("music");
        setHelpString("<genre>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String rawRequestedGenre = argStrings[0];
        RecommendationHandler recommendationHandler = new RecommendationHandler();
        List<String> availableGenres = recommendationHandler.getAvailableGenreSeeds();
        List<JSONObject> recommendedTracks = null;
        String requestedGenre = identifyRequestedGenre(rawRequestedGenre, availableGenres);
        MessageUtils.sendMessage(event, "Playing recommended tracks for genre: " + requestedGenre);
        recommendedTracks = ListUtilies.castList(JSONObject.class,
                recommendationHandler.getRecommendationsByGenre(requestedGenre).toList());
        if (recommendedTracks != null) {
            recommendedTracks = recommendedTracks.subList(0, 2);
            playRecommendedTracks(event, recommendedTracks);
        }
    }

    public void playRecommendedTracks(MessageReceivedEvent event, List<JSONObject> recommendedTracksToBePlayed) {
        recommendedTracksToBePlayed.forEach(recommendedTrack -> {
            String firstArtistName = TrackHandler
                    .getNameOfArtist(TrackHandler.getArtistsOfTrack(recommendedTrack).getJSONObject(0));
            String trackName = TrackHandler.getNameOfTrack(recommendedTrack);
            CommandManager.player.loadAndPlay(event, "ytsearch: " + firstArtistName + " " + trackName);
        });
    }

    private String identifyRequestedGenre(String rawRequestedGenre, List<String> availableGenres) {
        if (availableGenres.contains(rawRequestedGenre)) {
            return rawRequestedGenre;
        } else {
            float mostSimilarGenreScore = 10000;
            String mostSimilarGenre = null;
            StringDistance metric = StringDistances.levenshtein();

            for (String genre : availableGenres) {
                float similarityScore = metric.distance(rawRequestedGenre, genre);
                if (similarityScore < mostSimilarGenreScore) {
                    mostSimilarGenreScore = similarityScore;
                    mostSimilarGenre = genre;
                }

            }
            return mostSimilarGenre;
        }
    }
}