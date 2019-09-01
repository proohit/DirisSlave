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
        Guild guild = event.getGuild();

        if (guild != null) {
            if (argStrings.length >= 2) {
                String trackUrl = "ytsearch: ";
                for (int i = 1; i < argStrings.length; i++) {
                    trackUrl += argStrings[i] + " ";
                }
                AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(), event.getMember().getVoiceState().getChannel());
                Commands.player.loadAndPlay(event.getTextChannel(), trackUrl);
            }
        }
    }
}
