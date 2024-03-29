package bot.imageboards.commands;

import bot.imageboards.api.DanbooruHandler;
import bot.imageboards.exceptions.ImageNotFoundException;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DanbooruCommand extends Command {
    DanbooruHandler danbooruHandler = new DanbooruHandler();

    public DanbooruCommand() {
        addPermission("Bananenchefs");
        addCommendPrefix("danbooru");
        setDescription("Sexy pictures of hot waifus are waiting for you. Just add your tag");
        setTopic("images");
        setHelpString("<tag1> <tag2 (optional)>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length > 2) {
            MessageUtils.sendMessage(event, "`you cannot search for more than 2 tags!`");
            return;
        }
        if (argStrings.length == 1) {
            try {
                String imageUrl = danbooruHandler.getRandomImageByQuery(argStrings[0]);
                MessageUtils.sendMessage(event, imageUrl);
            } catch (ImageNotFoundException e) {
                sendSimilarTags(event, argStrings[0]);
            }
        }
        if (argStrings.length == 2) {
            try {
                String imageUrl = danbooruHandler.getRandomImageByQuery(argStrings[0], argStrings[1]);
                MessageUtils.sendMessage(event, imageUrl);
            } catch (Exception e) {
                MessageUtils.sendMessage(event, "Nothing found for those tags");
            }
        }
    }

    private void sendSimilarTags(MessageReceivedEvent event, String initialTag) {
        JSONArray similarTags = danbooruHandler.getTagsByQuery(initialTag);
        StringBuilder similarTagsString = new StringBuilder();
        similarTagsString.append("No tags found for ").append(initialTag).append(". Searching for similar tags...\n");
        similarTags.forEach(similarTagObject -> {
            JSONObject similarTag = (JSONObject) similarTagObject;
            similarTagsString.append(similarTag.getString("name")).append(", count: ")
                    .append(similarTag.getInt("post_count")).append("\n");
        });
        MessageUtils.sendMessage(event, similarTagsString.toString());
    }

}
