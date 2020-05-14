package audioplayer.commands;

import database.Song;
import database.SongHistoryTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.HashMap;
import java.util.Map;

import static main.Commands.sendBeautifulMessage;

public class HistoryCommand extends Command {
    public HistoryCommand() {
        setCommand(prefix + "history");
        addPermission("everyone");
        setTopic("music");
        setDescription("shows you already played songs");
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        StringBuilder result = new StringBuilder("History: \n");
        Map<Integer, Song> history = (HashMap<Integer, Song>) SongHistoryTable.getLastSongs();
        history.forEach((id, song) -> result.append(id).append(" ").append(song.toString()).append("\n"));
        sendBeautifulMessage(event, result.toString());
    }
}
