package imageboards.commands;

import imageboards.api.PixabayHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class LizardCommand extends Command {
    public LizardCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        PixabayHandler pixabayHandler = new PixabayHandler();
        String geckoImageUrl = pixabayHandler.getRandomImageUrlByQuery("gecko");
        main.Commands.sendMessage(event, geckoImageUrl);
    }

    @Override
    protected String defineCommand() {
        return prefix + "lizard";
    }

    @Override
    protected String defineDescription() {
        return ":lizard:";
    }

    @Override
    protected String defineTopic() {
        return "images";
    }

    @Override
    protected String defineHelpString() {
        return "";
    }
}
