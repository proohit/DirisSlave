package audioplayer.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.Iterator;

public class QueueCommand extends Command {
    public QueueCommand() {
        setCommand(prefix + "q");
        addPermission("everyone");
        setTopic("music");
        setDescription("shows the queue");
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
        StringBuilder queue = new StringBuilder("current queue: \n");
        Iterator<AudioTrack> it = Commands.player.getQueue(event.getTextChannel()).iterator();
        int i = 1;
        while (it.hasNext()) {
            queue.append(i++).append(" ").append(it.next().getInfo().title).append("\n");
        }
        Commands.sendBeautifulMessage(event, queue.toString());
    }
}
