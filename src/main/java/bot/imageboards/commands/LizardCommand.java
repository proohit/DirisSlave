package bot.imageboards.commands;

import bot.imageboards.api.PixabayHandler;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LizardCommand extends Command {
    public LizardCommand() {
        addPermission("everyone");
        addCommendPrefix("lizard");
        setDescription(":lizard:");
        setTopic("images");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        PixabayHandler pixabayHandler = new PixabayHandler();
        String geckoImageUrl = pixabayHandler.getRandomImageUrlByQuery("gecko");
        MessageUtils.sendMessage(event, geckoImageUrl);
    }

}
