package imageboards.commands;

import imageboards.api.PixabayHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class PixabayCommand extends Command {

    @Override
    protected String[] defineCommand() {
        return new String[] { "pixabay" };
    }

    @Override
    protected String defineDescription() {
        return "search for any pictures you want";
    }

    @Override
    protected String defineTopic() {
        return "images";
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

    @Override
    protected String defineHelpString() {
        return "<search term to search for>";
    }

    public PixabayCommand() {
        addPermission("everyone");
    }

}