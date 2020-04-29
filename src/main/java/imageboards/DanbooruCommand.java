package imageboards;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.UrlHandler;

public class DanbooruCommand extends Command {
    public DanbooruCommand() {
        setCommand(prefix + "danbooru");
        setPermission("Bananenchefs");
        setTopic("images");
        setDescription("Sexy pictures of hot waifus are waiting for you. Just add your tag");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length < 2) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        String URL = "https://danbooru.donmai.us/posts.json/?utf8=%E2%9C%93&limit=200&tags=";
        if (argStrings.length > 3)
            Commands.sendMessage(event, "`you cannot search for more than 2 tags!`");
        for (int i = 1; i < argStrings.length; i++) {
            argStrings[i] = argStrings[i].replace('-', '_');
            URL += argStrings[i] + "+";
        }
        if (argStrings.length == 2)
            Commands.sendMessage(event, getDanbooru(URL, argStrings[1]));
        if (argStrings.length == 3)
            Commands.sendMessage(event, getDanbooru(URL, argStrings[1], argStrings[2]));

    }

    private static String getDanbooruTags(String tag) {
        JsonArray object = UrlHandler.parseJsonArray(
                "https://danbooru.donmai.us/tags.json/?search[hide_empty]=true&search[order]=count&search[name_matches]="
                        + "*" + tag + "*");
        String tags = "`";
        for (int i = 0; i < object.size() / 2; i++) {
            JsonObject element = (JsonObject) object.get(i);
            tags += element.get("name") + " post count: " + element.get("post_count") + "\n";
        }
        tags += "`";
        return tags;
    }

    private static String getDanbooru(String URL, String tag1) {
        JsonArray object = UrlHandler.parseJsonArray(URL);
        if (object.size() == 0)
            return "no posts found, searching for matching tags\n" + tag1 + "\n" + getDanbooruTags(tag1);
        return UrlHandler.getRandomObject(object).get("file_url").getAsString();
    }

    private static String getDanbooru(String URL, String tag1, String tag2) {
        JsonArray object = UrlHandler.parseJsonArray(URL);
        if (object.size() == 0)
            return "no posts found, searching for matching tags\n" + tag1 + "\n" + getDanbooruTags(tag1) + "\n" + tag2
                    + "\n" + getDanbooruTags(tag2);
        return UrlHandler.getRandomObject(object).get("file_url").getAsString();
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<tag1> <tag2 (optional)>\n");

        return help.toString();
    }
}
