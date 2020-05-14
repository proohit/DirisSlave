package imageboards;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import imageboards.apis.DanbooruHandler;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.UrlHandler;

public class DanbooruCommand extends Command {
    public DanbooruCommand() {
        setCommand(prefix + "danbooru");
        addPermission("Bananenchefs");
        setTopic("images");
        setDescription("Sexy pictures of hot waifus are waiting for you. Just add your tag");
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<tag1> <tag2 (optional)>\n");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length < 1) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        if (argStrings.length > 2) {
            Commands.sendMessage(event, "`you cannot search for more than 2 tags!`");
            return;
        }
        DanbooruHandler danbooruHandler = new DanbooruHandler();
        if (argStrings.length == 1) {
            String imageUrl = danbooruHandler.getImageByQuery(argStrings[0]);
            Commands.sendMessage(event, imageUrl);
        }
        if (argStrings.length == 2) {
            String imageUrl = danbooruHandler.getImageByQuery(argStrings[0], argStrings[1]);
            Commands.sendMessage(event, imageUrl);
        }
    }
}
