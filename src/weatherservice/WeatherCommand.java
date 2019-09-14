package weatherservice;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import util.Command;
import util.UrlHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherCommand extends Command {
    public WeatherCommand() {
        setCommand(prefix + "weather");
        setPermission("everyone");
        setTopic("weather");
        setDescription("shows the weather for the next 15 hours");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 2) {
            final StringBuilder result = new StringBuilder();
            result.append("*The temperature in *" + argStrings[1] + "* for the next 15 hours* \n");

            JsonObject root = UrlHandler.parseJson("http://api.openweathermap.org/data/2.5/forecast?q=" + argStrings[1] + "&units=metric&appid=057f5d829942a1293eedbb094f8a5c71");

            JsonArray list = root.get("list").getAsJsonArray();


            final TimeSeries series = new TimeSeries("WeatherData");

            Double[] temperatures = new Double[6];
            String[] time = new String[6];
            String[] weather = new String[6];
            for (int i = 1; i < 6; i++) {
                temperatures[i] = Double.parseDouble(list.get(i).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                time[i] = list.get(i).getAsJsonObject().get("dt_txt").getAsString();
                weather[i] = list.get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
                String date = time[i].split(" ")[0];
                String timeString = time[i].split(" ")[1];
                int hour = Integer.parseInt(timeString.split(":")[0]);
                int day = Integer.parseInt(date.split("-")[2]);
                int month = Integer.parseInt(date.split("-")[1]);
                int year = Integer.parseInt(date.split("-")[0]);
                series.add(new Hour(hour, new Day(day, month, year)), temperatures[i]);
            }

            final TimeSeriesCollection dataset = new TimeSeriesCollection();
            dataset.addSeries(series);

            JFreeChart chart = ChartFactory.createTimeSeriesChart(
                    "Weather in " + argStrings[1] + " on " + time[1].split(" ")[0],
                    "time",
                    "temperature in °C",
                    dataset,
                    false,
                    false,
                    false
            );
            File file = new File("chart.png");
            try {
                ChartUtilities.saveChartAsPNG(file, chart, 800, 600);
                BufferedImage img = ImageIO.read(file);
                event.getAuthor().openPrivateChannel().queue(privateChannel -> privateChannel.sendFile(file, file.getName()).queue());
            } catch (IOException e) {
                e.printStackTrace();
            }

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
