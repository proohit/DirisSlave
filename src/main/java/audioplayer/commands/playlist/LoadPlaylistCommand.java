package audioplayer.commands.playlist;

import java.util.Arrays;
import java.util.List;

import audioplayer.AudioPlayer;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class LoadPlaylistCommand extends Command {
    private final List<String> SHUFFLE_PARAMETERS = Arrays.asList("shuffle", "random", "rnd");

    public LoadPlaylistCommand() {
        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Boolean isShuffle = false;
        if (argStrings.length >= 2) {
            isShuffle = SHUFFLE_PARAMETERS.contains(argStrings[1]);
        }
        if (argStrings.length >= 1) {
            String playlistNameToBeLoaded = argStrings[0];
            Commands.player.playPlaylist(event.getTextChannel(), playlistNameToBeLoaded, isShuffle);
            AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(),
                    event.getMember().getVoiceState().getChannel());
        }
    }

    @Override
    protected String defineCommand() {
        return "load";
    }

    @Override
    protected String defineDescription() {
        return "loads the playlist in the queue";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "<playlist name> [shuffle|random|rnd]";
    }

}
