package audioplayer.commands.play;

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
                trackUrl = argStrings[1];
            }

            Commands.player.loadAndPlay(event, trackUrl);
        }
    }
}
