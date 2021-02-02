package audioplayer.commands;

import static main.Commands.sendBeautifulMessage;

import java.util.Map;

import database.Song;
import database.SongHistoryTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class HistoryCommand extends Command {
    public HistoryCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        StringBuilder result = new StringBuilder("History: \n");
        Map<Integer, Song> history = SongHistoryTable.getLastSongs();
        history.forEach((id, song) -> result.append(id).append(" ").append(song.toString()).append("\n"));
        sendBeautifulMessage(event, result.toString());
    }

    @Override
    protected String[] defineCommand() {
        return new String[] { "history" };
    }

    @Override
    protected String defineDescription() {
        return "shows you already played songs";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "";
    }
}
