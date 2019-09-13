package weatherservice;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.UrlHandler;

public class WeatherCommand extends Command {
    public WeatherCommand() {
        setCommand(prefix+"weather");
        setPermission("everyone");
        setTopic("weather");
        setDescription("shows the weather for the next 15 hours");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if(argStrings.length == 2) {
            final StringBuilder result = new StringBuilder();
            result.append("*The temperature in *" +  argStrings[1]  + "* for the next 15 hours* \n");

            JsonObject root = UrlHandler.parseJson("http://api.openweathermap.org/data/2.5/forecast?q="+ argStrings[1] +"&units=metric&appid=057f5d829942a1293eedbb094f8a5c71");

            JsonArray list = root.get("list").getAsJsonArray();

            String[] temperatures = new String[6];
            String[] time = new String[6];
            String[] weather = new String[6];
            for (int i = 1; i < 6; i++) {
                temperatures[i] = list.get(i).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString();
                time[i] = list.get(i).getAsJsonObject().get("dt_txt").getAsString();
                weather[i] = list.get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
                result.append("time: " + time[i] + ", temperature: " + temperatures[i] + "°C, status: " + weather[i] + "\n");
            }
            result.append("\n");
            event.getAuthor().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(result.toString()).queue());

        }
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<city name>\n");

        return help.toString();
    }
}
