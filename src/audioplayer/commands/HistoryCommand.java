package audioplayer.commands;

import audioplayer.PlaylistManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.ArrayList;

import static main.Commands.sendBeautifulMessage;

public class HistoryCommand extends Command {
    public HistoryCommand() {
        setCommand(prefix + "history");
        setPermission("Bananenchefs");
        setTopic("music");
        setDescription("shows you already played songs");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            StringBuilder result = new StringBuilder("History: \n");
            ArrayList<String> history = PlaylistManager.getHistory();
            int i;
            if (history.size() < 10) {
                i = 1;
            } else {
                i = history.size() - 9;
            }
            for (; i <= history.size(); i++) {
                result.append(i).append(" ").append(history.get(i - 1)).append("\n");
            }
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
