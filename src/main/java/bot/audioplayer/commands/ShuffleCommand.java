package bot.audioplayer.commands;

import bot.main.CommandManager;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShuffleCommand extends Command {
    public ShuffleCommand() {
        addPermission("everyone");
        addCommendPrefix("shuffle");
        setDescription("bring some spice into the queue by shuffling it :man_dancing:");
        setTopic("music");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (CommandManager.player.shuffle(event.getGuild())) {
            MessageUtils.sendMessage(event, "shuffled the music :man_dancing:");
        } else {
            MessageUtils.sendBeautifulMessage(event, "queue is empty :( start rocking with pl!");
        }
    }

}
