package audioplayer.commands.play;

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
        setCommand(this.prefix + "pl");
        setTopic("music");
        setDescription("Play music by adding a search-term or by adding a link");

        this.addSubCommand(new PlayRecommendedCommand());

        this.addPermission("everyone");
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<search term>");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {

        if (argStrings.length <= 1) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        if (argStrings.length >= 2) {
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
