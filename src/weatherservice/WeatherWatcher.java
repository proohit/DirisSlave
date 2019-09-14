package weatherservice;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import util.UrlHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherWatcher {
    public static void start(JDA jda) {

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
                String result = "*The temperature in Stuttgart for the next 15 hours* \n";

                JsonObject root = UrlHandler.parseJson("http://api.openweathermap.org/data/2.5/forecast?q=Stuttgart&units=metric&appid=057f5d829942a1293eedbb094f8a5c71");

                JsonArray list = root.get("list").getAsJsonArray();
                final TimeSeries stuttgart = new TimeSeries("Stuttgart");
                final TimeSeries dortmund = new TimeSeries("Dortmund");

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
                    stuttgart.add(new Hour(hour, new Day(day, month, year)), temperatures[i]);
                }
                root = UrlHandler.parseJson("http://api.openweathermap.org/data/2.5/forecast?q=Dortmund&units=metric&appid=057f5d829942a1293eedbb094f8a5c71");
                list = root.get("list").getAsJsonArray();
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
                    dortmund.add(new Hour(hour, new Day(day, month, year)), temperatures[i]);
                }
                TimeSeriesCollection dataset = new TimeSeriesCollection();

                dataset.addSeries(stuttgart);
                dataset.addSeries(dortmund);

                JFreeChart chart = ChartFactory.createTimeSeriesChart(
                        "Weather in Stuttgart and Dortmund" + " on " + time[1].split(" ")[0],
                        "time",
                        "temperature in °C",
                        dataset,
                        true,
                        true,
                        false
                );
                XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart.getXYPlot().getRenderer();
                //enable data points labeling, gets the Y value of the item i1 in the series i
                renderer.setBaseItemLabelGenerator(new XYItemLabelGenerator() {
                    @Override
                    public String generateLabel(XYDataset xyDataset, int i, int i1) {
                        return String.valueOf(xyDataset.getYValue(i, i1));
                    }
                });
                renderer.setBaseItemLabelsVisible(true);
                File file = new File("chart.png");
                try {
                    ChartUtilities.saveChartAsPNG(file, chart, 800, 600);
                    TextChannel channel = jda.getTextChannelById("621021131675140096");
                    channel.sendFile(file, file.getName()).queue();
                    MessageHistory history = new MessageHistory(channel);
                    List<Message> msgs;
                    msgs = history.retrievePast(1).complete();
                    channel.purgeMessages(msgs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, timeTo6Am, 24 * 60 * 60 * 1000);
    }
}
