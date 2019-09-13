package weatherservice;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import util.UrlHandler;

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
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        long timeTo6Am = c.getTimeInMillis()-System.currentTimeMillis();
        task.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("new job");
                String result = "*The temperature in Stuttgart for the next 15 hours* \n";

                JsonObject root = UrlHandler.parseJson("http://api.openweathermap.org/data/2.5/forecast?q=Stuttgart&units=metric&appid=057f5d829942a1293eedbb094f8a5c71");

                JsonArray list = root.get("list").getAsJsonArray();

                String[] temperatures = new String[6];
                String[] time = new String[6];
                String[] weather = new String[6];
                for (int i = 1; i < 6; i++) {
                    temperatures[i] = list.get(i).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString();
                    time[i] = list.get(i).getAsJsonObject().get("dt_txt").getAsString();
                    weather[i] = list.get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
                    result += "time: " + time[i] + ", temperature: " + temperatures[i] + "°C, status: " + weather[i] + "\n";
                }
                result+="\n *The weather in Dortmund for the next 15 hours*\n";
                root = UrlHandler.parseJson("http://api.openweathermap.org/data/2.5/forecast?q=Dortmund&units=metric&appid=057f5d829942a1293eedbb094f8a5c71");
                list = root.get("list").getAsJsonArray();
                for (int i = 1; i < 6; i++) {
                    temperatures[i] = list.get(i).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString();
                    time[i] = list.get(i).getAsJsonObject().get("dt_txt").getAsString();
                    weather[i] = list.get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
                    result += "time: " + time[i] + ", temperature: " + temperatures[i] + "°C, status: " + weather[i] + "\n";
                }
                TextChannel channel = jda.getTextChannelById("621021131675140096");
                MessageHistory history = new MessageHistory(channel);
                List<Message> msgs;
                msgs = history.retrievePast(1).complete();
                channel.purgeMessages(msgs);

                channel.sendMessage(result).queue();

            }
        }, timeTo6Am, 24*60*60*1000);
    }
}
