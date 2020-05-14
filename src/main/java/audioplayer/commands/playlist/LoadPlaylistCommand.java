package audioplayer.commands.playlist;

import audioplayer.AudioPlayer;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class LoadPlaylistCommand extends Command {
    public LoadPlaylistCommand() {
        this.setCommand("load");
        this.setDescription("");
        this.setHelpString("");
        this.setTopic("music");

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

}
