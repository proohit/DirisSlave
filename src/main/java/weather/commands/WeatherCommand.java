package weather.commands;

import java.io.File;

import kong.unirest.json.JSONArray;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;
import weather.WeatherChart;
import weather.api.OpenWeatherMapHandler;

public class WeatherCommand extends Command {
    public WeatherCommand() {
        addPermission("everyone");
        addCommendPrefix("weather");
        setDescription("shows the weather for the next 15 hours");
        setTopic("weather");
        setHelpString("<city name>\n");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String query = buildQuery(argStrings);

        OpenWeatherMapHandler openWeatherMapHandler = new OpenWeatherMapHandler();

        JSONArray list = openWeatherMapHandler.getWeatherDataOfCity(query);

        WeatherChart chart = new WeatherChart(list, query);
        File chartFile = chart.buildChart();
        event.getAuthor().openPrivateChannel()
                .queue(privateChannel -> privateChannel.sendFile(chartFile, chartFile.getName()).queue());

    }

    private String buildQuery(String... argStrings) {
        String query = "";
        for (String subString : argStrings) {
            query += subString + " ";
        }
        return query;
    }

}
