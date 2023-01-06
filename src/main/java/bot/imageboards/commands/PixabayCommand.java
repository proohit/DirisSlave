package bot.imageboards.commands;

import bot.imageboards.api.PixabayHandler;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PixabayCommand extends Command {

    public PixabayCommand() {
        addPermission("everyone");
        addCommendPrefix("pixabay");
        setDescription("search for any pictures you want");
        setTopic("images");
        setHelpString("<search term to search for>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String searchQuery = String.join(" ", argStrings);
        PixabayHandler pixabayHandler = new PixabayHandler();
        String imageUrl = pixabayHandler.getRandomImageUrlByQuery(searchQuery);

        MessageUtils.sendMessage(event, imageUrl);
    }

}