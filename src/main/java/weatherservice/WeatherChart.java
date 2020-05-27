package weatherservice;

import java.io.File;
import java.io.IOException;

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
import kong.unirest.json.JSONObject;

public class WeatherChart {

    private final TimeSeries series = new TimeSeries("WeatherData");
    final TimeSeriesCollection dataset = new TimeSeriesCollection();
    private JSONArray weatherData;
    OpenWeatherMapHandler openWeatherMapHandler = new OpenWeatherMapHandler();
    String query;
    private int maxWeatherPoints = 6;

    public WeatherChart(JSONArray weatherData, String query) {
        this.weatherData = weatherData;
        this.query = query;
    }

    public File buildChart() {
        for (int i = 1; i < maxWeatherPoints; i++) {
            JSONObject weatherDate = weatherData.getJSONObject(i);
            addDataToSeries(weatherDate);
        }
        dataset.addSeries(this.series);

        JFreeChart chart = ChartFactory.createTimeSeriesChart("Weather in " + query + " today", "time",
                "temperature in °C", dataset, false, false, false);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart.getXYPlot().getRenderer();
        // enable data points labeling, gets the Y value of the item i1 in the series i
        setLabels(renderer);
        renderer.setBaseItemLabelsVisible(true);
        return createChartFile(chart);
    }

    public File createChartFile(JFreeChart chart) {
        File file = new File("chart.png");
        try {
            ChartUtilities.saveChartAsPNG(file, chart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void addDataToSeries(JSONObject weatherData) {
        Double temperature = openWeatherMapHandler.getTemperatuteOfWeatherData(weatherData);
        String time = openWeatherMapHandler.getTimeOfWeatherData(weatherData);
        String date = time.split(" ")[0];
        String timeString = time.split(" ")[1];
        int hour = Integer.parseInt(timeString.split(":")[0]);
        int day = Integer.parseInt(date.split("-")[2]);
        int month = Integer.parseInt(date.split("-")[1]);
        int year = Integer.parseInt(date.split("-")[0]);
        series.add(new Hour(hour, new Day(day, month, year)), temperature);
    }

    public void setLabels(XYLineAndShapeRenderer renderer) {
        String[] weather = new String[maxWeatherPoints];
        for (int i = 1; i < maxWeatherPoints; i++) {
            JSONObject weatherDate = weatherData.getJSONObject(i);
            weather[i] = openWeatherMapHandler.getWeatherConditionOfWeatherData(weatherDate);
        }
        renderer.setBaseItemLabelGenerator(new XYItemLabelGenerator() {
            @Override
            public String generateLabel(XYDataset xyDataset, int i, int i1) {
                return String.valueOf(xyDataset.getYValue(i, i1)) + " " + weather[i1 + 1];
            }
        });
    }

}