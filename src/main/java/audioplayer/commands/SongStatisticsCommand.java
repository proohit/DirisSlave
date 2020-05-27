package audioplayer.commands;

import database.Song;
import database.SongHistoryTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.Comparator;
import java.util.Map;

public class SongStatisticsCommand extends Command {
    public SongStatisticsCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {

        Map<Song, Integer> statistics = SongHistoryTable.getSongStatistics();
        StringBuilder result = new StringBuilder("Song statistics: \n");

        statistics.entrySet().stream().sorted(new Comparator<Map.Entry<Song, Integer>>() {
            @Override
            public int compare(Map.Entry<Song, Integer> o1, Map.Entry<Song, Integer> o2) {
                if (o1.getValue() > o2.getValue())
                    return -1;
                else
                    return 1;
            }
        }).forEach(sortedItem -> {
            result.append(sortedItem.getValue()).append(" times, ").append(sortedItem.getKey().getTitle()).append("\n");
        });
        main.Commands.sendBeautifulMessage(event, result.toString());
    }

    @Override
    protected String defineCommand() {
        return prefix + "statistics";
    }

    @Override
    protected String defineDescription() {
        return "displays the top 10 most played songs";
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
