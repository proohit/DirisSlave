package audioplayer;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.ArrayList;

public class LoadplaylistCommand extends Command {
    public LoadplaylistCommand() {
        setCommand(prefix + "loadplaylist");
        setPermission("Bananenchefs");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 2) {
            AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(), event.getMember().getVoiceState().getChannel());
            Commands.player.playPlaylist(event.getTextChannel(), argStrings[1]);
            Commands.sendBeautifulMessage(event, "loaded playlist " + argStrings[1]);
        }
    }
}
