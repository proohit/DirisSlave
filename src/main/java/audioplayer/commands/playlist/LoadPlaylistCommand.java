package audioplayer.commands.playlist;

import audioplayer.AudioPlayer;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class LoadPlaylistCommand extends Command {
    public LoadPlaylistCommand() {
        this.addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            String playlistNameToBeLoaded = argStrings[0];
            if (Commands.player.playPlaylist(event.getTextChannel(), playlistNameToBeLoaded)) {
                AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(),
                        event.getMember().getVoiceState().getChannel());
                Commands.sendBeautifulMessage(event, "loaded playlist " + playlistNameToBeLoaded);
            } else {
                main.Commands.sendBeautifulMessage(event, playlistNameToBeLoaded + " not found or no songs available");
            }
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
        return "<playlist name>";
    }

}
