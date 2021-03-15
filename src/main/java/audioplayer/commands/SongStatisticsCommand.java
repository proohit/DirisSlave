package audioplayer.commands;

import java.util.Comparator;
import java.util.Map;

import database.Song;
import database.SongHistoryTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class SongStatisticsCommand extends Command {
    public SongStatisticsCommand() {
        addPermission("everyone");
        addCommendPrefix("statistics");
        setDescription("displays the top 10 most played songs");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {

        Map<Song, Integer> statistics = SongHistoryTable.getSongStatistics();
        StringBuilder result = new StringBuilder("Song statistics: \n");

        statistics.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(sortedItem -> {
                    result.append(sortedItem.getValue()).append(" times, ").append(sortedItem.getKey().getTitle())
                            .append("\n");
                });
        main.MessageUtils.sendBeautifulMessage(event, result.toString());
    }

}
