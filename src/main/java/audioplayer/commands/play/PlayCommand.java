package audioplayer.commands.play;

import main.CommandManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class PlayCommand extends Command {
    public PlayCommand() {
        addSubCommand(new PlayRecommendedCommand());
        addSubCommand(new PlayRandomCommand());
        addPermission("everyone");
        addCommendPrefix("pl", "play");
        setTopic("music");
        setDescription("Play music by adding a search-term or by adding a link");
        setHelpString("<youtube search term> | <youtube url>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String trackUrl = "";
        if (!(argStrings[0].contains("http") || argStrings[0].contains("https"))) {
            trackUrl = "ytsearch: ";
            for (int i = 0; i < argStrings.length; i++) {
                trackUrl += argStrings[i] + " ";
            }
        } else {
            trackUrl = argStrings[0];
        }

        CommandManager.player.loadAndPlay(event, trackUrl);
    }

    public void handle(Guild guild, String[] argStrings) {
        String trackUrl = "";
        if (!(argStrings[0].contains("http") || argStrings[0].contains("https"))) {
            trackUrl = "ytsearch: ";
            for (int i = 0; i < argStrings.length; i++) {
                trackUrl += argStrings[i] + " ";
            }
        } else {
            trackUrl = argStrings[0];
        }

        CommandManager.player.loadAndPlay(guild, trackUrl);
    }

}
