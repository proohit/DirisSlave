package weatherservice;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import api.OpenWeatherMapHandler;
import kong.unirest.json.JSONArray;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;

public class WeatherWatcher {
    public static void start(JDA jda) {
        OpenWeatherMapHandler openWeatherMapHandler = new OpenWeatherMapHandler();

        Timer task = new Timer();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 6);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long timeTo6Am = c.getTimeInMillis() - System.currentTimeMillis();
        task.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("new job");

                final JSONArray dortmundData = openWeatherMapHandler.getWeatherDataOfCity("Dortmund");
                final JSONArray stuttgartData = openWeatherMapHandler.getWeatherDataOfCity("Stuttgart");
                final TimeSeries stuttgart = new TimeSeries("Stuttgart");
                final TimeSeries dortmund = new TimeSeries("Dortmund");

                Double[] dortmundTemperatures = new Double[6];
                String[] dortmundTime = new String[6];
                String[] dortmundWeather = new String[6];
                for (int i = 1; i < 6; i++) {
                    dortmundTemperatures[i] = openWeatherMapHandler
                            .getTemperatuteOfWeatherData(dortmundData.getJSONObject(i));
                    dortmundTime[i] = openWeatherMapHandler.getTimeOfWeatherData(dortmundData.getJSONObject(i));
                    dortmundWeather[i] = openWeatherMapHandler
                            .getWeatherConditionOfWeatherData(dortmundData.getJSONObject(i));
                    String date = dortmundTime[i].split(" ")[0];
                    String timeString = dortmundTime[i].split(" ")[1];
                    int hour = Integer.parseInt(timeString.split(":")[0]);
                    int day = Integer.parseInt(date.split("-")[2]);
                    int month = Integer.parseInt(date.split("-")[1]);
                    int year = Integer.parseInt(date.split("-")[0]);
                    stuttgart.add(new Hour(hour, new Day(day, month, year)), dortmundTemperatures[i]);
                }

                String[] stuttgartWeather = new String[6];
                for (int i = 1; i < 6; i++) {
                    dortmundTemperatures[i] = openWeatherMapHandler
                            .getTemperatuteOfWeatherData(stuttgartData.getJSONObject(i));
                    dortmundTime[i] = openWeatherMapHandler.getTimeOfWeatherData(stuttgartData.getJSONObject(i));
                    stuttgartWeather[i] = openWeatherMapHandler
                            .getWeatherConditionOfWeatherData(stuttgartData.getJSONObject(i));
                    String date = dortmundTime[i].split(" ")[0];
                    String timeString = dortmundTime[i].split(" ")[1];
                    int hour = Integer.parseInt(timeString.split(":")[0]);
                    int day = Integer.parseInt(date.split("-")[2]);
                    int month = Integer.parseInt(date.split("-")[1]);
                    int year = Integer.parseInt(date.split("-")[0]);
                    dortmund.add(new Hour(hour, new Day(day, month, year)), dortmundTemperatures[i]);
                }
                TimeSeriesCollection dataset = new TimeSeriesCollection();

                dataset.addSeries(stuttgart);
                dataset.addSeries(dortmund);

                JFreeChart chart = ChartFactory.createTimeSeriesChart("Weather in Stuttgart and Dortmund today", "time",
                        "temperature in °C", dataset, true, true, false);
                XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart.getXYPlot().getRenderer();
                // enable data points labeling, gets the Y value of the item i1 in the series i
                renderer.setBaseItemLabelGenerator(new XYItemLabelGenerator() {
                    @Override
                    public String generateLabel(XYDataset xyDataset, int i, int i1) {
                        if (i == 0) {
                            return String.valueOf(xyDataset.getYValue(i, i1)) + " " + dortmundWeather[i1 + 1];
                        } else {
                            return String.valueOf(xyDataset.getYValue(i, i1)) + " " + stuttgartWeather[i1 + 1];
                        }
                    }
                });
                renderer.setBaseItemLabelsVisible(true);
                File file = new File("chart.png");
                try {
                    ChartUtilities.saveChartAsPNG(file, chart, 800, 600);
                    TextChannel channel = jda.getTextChannelById("621021131675140096");
                    MessageHistory history = new MessageHistory(channel);
                    List<Message> msgs;
                    msgs = history.retrievePast(1).complete();
                    channel.purgeMessages(msgs);
                    channel.sendFile(file, file.getName()).queue();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, timeTo6Am, 24 * 60 * 60 * 1000);
    }
}
