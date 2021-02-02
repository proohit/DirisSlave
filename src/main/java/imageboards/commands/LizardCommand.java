package imageboards.commands;

import imageboards.api.PixabayHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

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
        main.Commands.sendMessage(event, geckoImageUrl);
    }

}
