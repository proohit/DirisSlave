package audioplayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class SearchResultEmbed {

    EmbedBuilder eb;

    public SearchResultEmbed(AudioTrack track) {
        eb = new EmbedBuilder();
        int seconds = (int) track.getDuration() / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        String secondsString = String.valueOf(seconds);
        String minutesString = String.valueOf(minutes);
        if (secondsString.length() < 2)
            secondsString = "0" + secondsString;
        if (minutesString.length() < 2)
            minutesString = "0" + minutesString;
        eb.setTitle(track.getInfo().title, track.getInfo().uri);
        eb.setDescription("Adding the song to the queue");
        eb.addField("Title", track.getInfo().title, false);
        eb.addField("Duration", minutesString + ":" + secondsString, true);
        eb.setThumbnail("https://img.youtube.com/vi/" + track.getIdentifier() + "/0.jpg");
        eb.setAuthor(track.getInfo().author);
    }

    public MessageEmbed getEmbed() {
        return eb.build();
    }
}
