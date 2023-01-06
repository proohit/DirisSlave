package bot.audioplayer.commands;

import java.util.Comparator;
import java.util.Map;

import bot.database.Song;
import bot.database.SongHistoryTable;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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
        SongHistoryTable.getLastSongs(event.getGuild().getIdLong()).entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).forEach(entry -> {
                    Song song = entry.getValue();
                    result.append(song.getTitle()).append("\n").append(song.getUrl()).append("\n\n");
                });
        MessageUtils.sendBeautifulMessage(event, result.toString());
    }

}
