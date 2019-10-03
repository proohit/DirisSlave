package audioplayer.commands;

import audioplayer.PlaylistManager;
import database.Song;
import database.SongHistoryTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.ArrayList;

import static main.Commands.sendBeautifulMessage;

public class HistoryCommand extends Command {
    public HistoryCommand() {
        setCommand(prefix + "history");
        setPermission("everyone");
        setTopic("music");
        setDescription("shows you already played songs");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            StringBuilder result = new StringBuilder("History: \n");
            ArrayList<Song> history = (ArrayList<Song>) SongHistoryTable.getLastSongs();
            history.stream().forEach(song -> result.append(song.toString()).append("\n"));
            sendBeautifulMessage(event, result.toString());
        }
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand()+"***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }
}
