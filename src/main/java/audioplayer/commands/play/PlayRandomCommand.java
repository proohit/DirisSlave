package audioplayer.commands.play;

import java.util.List;

import org.simmetrics.StringDistance;
import org.simmetrics.metrics.StringDistances;

import audioplayer.api.RecommendationHandler;
import audioplayer.api.TrackHandler;
import kong.unirest.json.JSONObject;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;
import shared.util.ListUtilies;

public class PlayRandomCommand extends Command {
    public PlayRandomCommand() {
        addPermission("everyone");
        addCommendPrefix("random", "genre");
        setDescription("plays random songs based on genre");
        setTopic("music");
        setHelpString("<genre>");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length <= 0) {
            Commands.sendMessage(event, getHelp());
        } else {
            String rawRequestedGenre = argStrings[0];
            RecommendationHandler recommendationHandler = new RecommendationHandler();
            List<String> availableGenres = recommendationHandler.getAvailableGenreSeeds();
            List<JSONObject> recommendedTracks = null;
            String requestedGenre = identifyRequestedGenre(rawRequestedGenre, availableGenres);
            System.out.println(requestedGenre);
            Commands.sendMessage(event, "Playing recommended tracks for genre: " + requestedGenre);
            recommendedTracks = ListUtilies.castList(JSONObject.class,
                    recommendationHandler.getRecommendationsByGenre(requestedGenre).toList());
            if (recommendedTracks != null) {
                recommendedTracks = recommendedTracks.subList(0, 2);
                playRecommendedTracks(event, recommendedTracks);
            }
        }
    }

    public void playRecommendedTracks(MessageReceivedEvent event, List<JSONObject> recommendedTracksToBePlayed) {
        recommendedTracksToBePlayed.forEach(recommendedTrack -> {
            String firstArtistName = TrackHandler
                    .getNameOfArtist(TrackHandler.getArtistsOfTrack(recommendedTrack).getJSONObject(0));
            String trackName = TrackHandler.getNameOfTrack(recommendedTrack);
            Commands.player.loadAndPlay(event, "ytsearch: " + firstArtistName + " " + trackName);
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