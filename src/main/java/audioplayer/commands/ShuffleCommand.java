package audioplayer.commands;

import main.CommandManager;
import main.MessageUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class ShuffleCommand extends Command {
    public ShuffleCommand() {
        addPermission("everyone");
        addCommendPrefix("shuffle");
        setDescription("bring some spice into the queue by shuffling it :man_dancing:");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (CommandManager.player.shuffle(event.getTextChannel())) {
            MessageUtils.sendMessage(event, "shuffled the music :man_dancing:");
        } else {
            MessageUtils.sendBeautifulMessage(event, "queue is empty :( start rocking with pl!");
        }
    }

}
