package audioplayer.commands.play;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class PlayCommand extends Command {
    public PlayCommand() {
        this.addSubCommand(new PlayRecommendedCommand());
        this.addSubCommand(new PlayRandomCommand());
        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {

        if (argStrings.length < 1) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        if (argStrings.length >= 1) {
            String trackUrl = "";
            if (!(argStrings[0].contains("http") || argStrings[0].contains("https"))) {
                trackUrl = "ytsearch: ";
                for (int i = 0; i < argStrings.length; i++) {
                    trackUrl += argStrings[i] + " ";
                }
            } else {
                trackUrl = argStrings[0];
            }

            Commands.player.loadAndPlay(event, trackUrl);
        }
    }

    @Override
    protected String defineCommand() {
        return this.prefix + "pl";
    }

    @Override
    protected String defineDescription() {
        return "Play music by adding a search-term or by adding a link";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "<youtube search term> | <youtube url>";
    }
}
