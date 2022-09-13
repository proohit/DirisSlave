package audioplayer.commands;

import java.util.Iterator;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import main.CommandManager;
import main.MessageUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class QueueCommand extends Command {
    public QueueCommand() {
        addPermission("everyone");
        addCommendPrefix("q", "queue");
        setDescription("shows the queue");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        StringBuilder queue = new StringBuilder("current queue: \n");
        Iterator<AudioTrack> it = CommandManager.player.getQueue(event.getGuild()).iterator();
        int i = 1;
        while (it.hasNext()) {
            queue.append(i++).append(" ").append(it.next().getInfo().title).append("\n");
        }
        MessageUtils.sendBeautifulMessage(event, queue.toString());
    }

}
