package imageboards.commands;

import imageboards.api.PixabayHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class PixabayCommand extends Command {

    public PixabayCommand() {
        addPermission("everyone");
        addCommendPrefix("pixabay");
        setDescription("search for any pictures you want");
        setTopic("images");
        setHelpString("<search term to search for>");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 1) {
            String searchQuery = "";
            for (String subString : argStrings) {
                searchQuery += subString + " ";
            }
            PixabayHandler pixabayHandler = new PixabayHandler();
            String imageUrl = pixabayHandler.getRandomImageUrlByQuery(searchQuery);

            Commands.sendMessage(event, imageUrl);
        }
    }

}