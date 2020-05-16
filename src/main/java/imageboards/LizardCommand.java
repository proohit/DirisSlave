package imageboards;

import api.PixabayHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class LizardCommand extends Command {
    public LizardCommand() {
        setCommand(prefix + "lizard");
        addPermission("everyone");
        setTopic("images");
        setDescription(":lizard:");
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        PixabayHandler pixabayHandler = new PixabayHandler();
        String geckoImageUrl = pixabayHandler.getRandomImageUrlByQuery("gecko");
        main.Commands.sendMessage(event, geckoImageUrl);
    }
}
