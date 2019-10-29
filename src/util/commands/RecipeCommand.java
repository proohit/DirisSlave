package util.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.UrlHandler;

public class RecipeCommand extends Command {
    public RecipeCommand() {
        setCommand(prefix + "recipe");
        setPermission("everyone");
        setTopic("util");
        setDescription("displays you a recipe containing specific ingredients");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length < 2) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        String URL = "https://www.food2fork.com/api/search?key=ffb26f279a14aaf6b21493d4ebccb867&q=";
        for (int i = 1; i < argStrings.length; i++) {
            URL += argStrings[i] + "%20";
        }
        main.Commands.sendMessage(event, getRecipe(URL));
    }

    private String getRecipe(String URL) {
        JsonObject jObject = UrlHandler.parseJson(URL);
        if (jObject.get("count").getAsInt() == 0) {
            return "no recipes found";
        }
        JsonArray object = (JsonArray) jObject.get("recipes");

        return UrlHandler.getRandomObject(object).get("source_url").getAsString();
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<ingredient1> <ingredient2>...\n");

        return help.toString();
    }
}
