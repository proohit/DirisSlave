package audioplayer.commands.playlist;

import java.util.Arrays;
import java.util.List;

import audioplayer.AudioPlayer;
import main.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class LoadPlaylistCommand extends Command {
    private final List<String> SHUFFLE_PARAMETERS = Arrays.asList("shuffle", "random", "rnd");

    public LoadPlaylistCommand() {
        addPermission("everyone");
        addCommendPrefix("load", "pl", "play");
        setDescription("loads the playlist in the queue. can also be loaded shuffled");
        setTopic("music");
        setHelpString("<playlist name> [shuffle|random|rnd]");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        Boolean isShuffle = false;
        if (argStrings.length >= 2) {
            isShuffle = SHUFFLE_PARAMETERS.contains(argStrings[1]);
        }
        if (argStrings.length >= 1) {
            String playlistNameToBeLoaded = argStrings[0];
            CommandManager.player.playPlaylist(event.getGuild(), event.getChannel(), playlistNameToBeLoaded, isShuffle);
            AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(),
                    event.getMember().getVoiceState().getChannel());
        }
    }
}
