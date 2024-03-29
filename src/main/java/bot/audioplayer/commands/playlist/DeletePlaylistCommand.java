package bot.audioplayer.commands.playlist;

import bot.database.PlaylistTable;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DeletePlaylistCommand extends Command {
    public DeletePlaylistCommand() {
        addPermission("everyone");
        addCommendPrefix("delete", "del");
        setDescription("Deletes the playlist with the given name");
        setTopic("music");
        setHelpString("<playlist name>>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String result;
        result = PlaylistTable.deletePlaylist(argStrings[0], event.getGuild().getIdLong()) == 1
                ? "deleted playlist " + argStrings[0]
                : "playlist " + argStrings[0] + " not found";
        MessageUtils.sendBeautifulMessage(event, result);
    }

}
