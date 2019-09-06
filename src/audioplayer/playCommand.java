package audioplayer;

import main.Commands;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class playCommand extends Command {
    public playCommand() {
        setCommand(prefix + "pl");
        setPermission("Bananenchefs");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
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

                AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(), event.getMember().getVoiceState().getChannel());
                Commands.player.loadAndPlay(event.getTextChannel(), trackUrl);
        }
    }
}
