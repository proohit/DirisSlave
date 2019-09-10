package imageboards;

import com.google.gson.JsonObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.UrlHandler;

import java.util.Random;

public class LizardCommand extends Command {
    public LizardCommand() {
        setCommand(prefix+"lizard");
        setPermission("everyone");
        setTopic("images");
        setDescription(":lizard:");
    }
    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        Random random = new Random();
        int rand = random.nextInt(4);
        while(rand == 0) rand = random.nextInt(4);
        System.out.println(rand);
        JsonObject element = UrlHandler.getRandomObject(UrlHandler.parseJson("https://pixabay.com/api/?key=13552323-5229d6fb4315b7d72aadad878&page="+rand+"&per_page=200&q=gecko").get("hits").getAsJsonArray());
        main.Commands.sendMessage(event, element.get("largeImageURL").getAsString());
    }

    @Override
    public String getHelp() {
        return null;
    }
}
