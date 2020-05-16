package weatherservice;

import java.io.File;

import api.OpenWeatherMapHandler;
import kong.unirest.json.JSONArray;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class WeatherCommand extends Command {
    public WeatherCommand() {
        setCommand(prefix + "weather");
        addPermission("everyone");
        setTopic("weather");
        setDescription("shows the weather for the next 15 hours");
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<city name>\n");

        return help.toString();
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
}
