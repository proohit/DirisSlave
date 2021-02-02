package audioplayer.commands;

import java.util.Iterator;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class QueueCommand extends Command {
    public QueueCommand() {
        addPermission("everyone");
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

    @Override
    protected String[] defineCommand() {
        return new String[] { "q", "queue" };
    }

    @Override
    protected String defineDescription() {
        return "shows the queue";
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
