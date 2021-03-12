package imageboards.commands;

import imageboards.api.PixabayHandler;
import main.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

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

        CommandManager.sendMessage(event, imageUrl);
    }

}