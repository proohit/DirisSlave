package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class SeekCommand extends Command {
    public SeekCommand() {
        setCommand(prefix+"seek");
        setPermission("Bananenchefs");
        setTopic("music");
        setDescription("seeks forward in the current playing song");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if(argStrings.length == 2) {
            int inputSeconds = Integer.parseInt(argStrings[1]);
            if(inputSeconds<0) return;
            long position = Commands.player.seek(event.getTextChannel(),inputSeconds);
            if(position > 0) {
                long seconds = position/1000;
                int minutes = (int)seconds/60;
                seconds = seconds%60;
                Commands.sendBeautifulMessage(event, "skipped to minute " + minutes + ":" + seconds);
            }
        }
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<seconds to skip in the current song>");

        return help.toString();
    }
}
