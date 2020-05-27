package weatherservice;

import java.io.File;

import api.OpenWeatherMapHandler;
import kong.unirest.json.JSONArray;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class WeatherCommand extends Command {
    public WeatherCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 1) {
            String query = buildQuery(argStrings);

            OpenWeatherMapHandler openWeatherMapHandler = new OpenWeatherMapHandler();

            JSONArray list = openWeatherMapHandler.getWeatherDataOfCity(query);

            WeatherChart chart = new WeatherChart(list, query);
            File chartFile = chart.buildChart();
            event.getAuthor().openPrivateChannel()
                    .queue(privateChannel -> privateChannel.sendFile(chartFile, chartFile.getName()).queue());
        }

    }

    private String buildQuery(String... argStrings) {
        String query = "";
        for (String subString : argStrings) {
            query += subString + " ";
        }
        return query;
    }

    @Override
    protected String defineCommand() {
        return prefix + "weather";
    }

    @Override
    protected String defineDescription() {
        return "shows the weather for the next 15 hours";
    }

    @Override
    protected String defineTopic() {
        return "weather";
    }

    @Override
    protected String defineHelpString() {
        return "<city name>\n";
    }
}
