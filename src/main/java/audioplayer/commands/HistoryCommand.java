package audioplayer.commands;

import java.util.Comparator;
import java.util.Map;

import database.SongHistoryTable;
import main.MessageUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class HistoryCommand extends Command {
    public HistoryCommand() {
        addPermission("everyone");
        addCommendPrefix("history");
        setDescription("shows you already played songs");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        StringBuilder result = new StringBuilder("History: \n");
        SongHistoryTable.getLastSongs().entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .forEach(entry -> result.append(entry.getValue().toString()).append("\n"));
        MessageUtils.sendBeautifulMessage(event, result.toString());
    }

}
