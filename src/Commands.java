import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import audioplayer.AudioPlayer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Commands {

    static String prefix = "#";
    static HashMap<String, String> permissions = new HashMap<String, String>();
    static AudioPlayer player;

    public Commands() {
        player = new AudioPlayer();
        permissions.put(prefix + "del", "Bananenchefs");
        permissions.put(prefix + "coffe", "everyone");
        permissions.put(prefix + "gah", "everyone");
        permissions.put(prefix + "recipe", "everyone");
        permissions.put(prefix + "hentai", "Bananenchefs");
        permissions.put(prefix + "neko", "Bananenchefs");
        permissions.put(prefix + "pgif", "Bananenchefs");
        permissions.put(prefix + "thigh", "Bananenchefs");
        permissions.put(prefix + "danbooru", "Bananenchefs");
        permissions.put(prefix + "calc", "everyone");
        permissions.put(prefix + "yt", "everyone");
        permissions.put(prefix + "stats", "everyone");
        permissions.put(prefix + "help", "everyone");
        permissions.put(prefix + "pl", "Bananenchefs");
        permissions.put(prefix + "play", "Bananenchefs");
        permissions.put(prefix + "skip", "Bananenchefs");
        permissions.put(prefix + "stop", "Bananenchefs");
        permissions.put(prefix + "queue", "Bananenchefs");
        permissions.put(prefix + "q", "Bananenchefs");
        permissions.put(prefix + "remove", "Bananenchefs");
        permissions.put(prefix + "rm", "Bananenchefs");
        permissions.put(prefix + "skipto", "Bananenchefs");

    }

    private static boolean isAllowed(Member member, String command) {
        boolean isAllowed = false;
        if (permissions.get(command).equals("everyone")) return true;
        for (Role role : member.getRoles()) {
            if (role.getName().equals(permissions.get(command)))
                isAllowed = true;
        }
        return isAllowed;
    }

    public void handle(MessageReceivedEvent event) {
        String[] argStrings = getArgs(event);
        if (!permissions.containsKey(argStrings[0])) return;
        if (!isAllowed(event.getMember(), argStrings[0]))
            return;
        switch (argStrings[0]) {
            case "#del":
                clear(event, argStrings);
                break;
            case "#coffe":
                coffee(event, argStrings);
                break;
            case "#gah":
                gah(event, argStrings);
                break;
            case "#recipe":
                recipes(event, argStrings);
                break;
            case "#hentai":
                hentai(event, argStrings);
                break;
            case "#neko":
                neko(event, argStrings);
                break;
            case "#pgif":
                pgif(event, argStrings);
                break;
            case "#thigh":
                thigh(event, argStrings);
                break;
            case "#danbooru":
                danbooru(event, argStrings);
                break;
            case "#calc":
                calculate(event, argStrings);
                break;
            case "#help":
                help(event);
                break;
            case "#yt":
                addYt(event, argStrings);
                break;
            case "#stats":
                stats(event);
                break;
            case "#pl":
            case "#play":
                play(event, argStrings);
                break;
            case "#skip":
                skip(event, argStrings);
                break;
            case "#stop":
                stop(event, argStrings);
                break;
            case "#q":
            case "#queue":
                queue(event);
                break;
            case "#rm":
            case "#remove":
                remove(event, argStrings);
                break;
            case "#skipto":
                skipTo(event, argStrings);
                break;
        }
    }

    private static void skipTo(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 2) {
            try {
                player.skipTo(Integer.parseInt(argStrings[1]), event.getTextChannel());
            } catch (NumberFormatException e) {
                sendBeautifulMessage(event, "the position you have entered is invalid");

            }
        }
    }

    private static void remove(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 2) {
            try {
                player.remove(Integer.parseInt(argStrings[1]), event.getTextChannel());
            } catch (NumberFormatException e) {
                sendBeautifulMessage(event, "the position you have entered is invalid");
            }
        }
    }

    private static void queue(MessageReceivedEvent event) {
        String queue = "current queue: \n";
        Iterator<AudioTrack> it = player.getQueue(event.getTextChannel()).iterator();
        int i = 1;
        while (it.hasNext()) {
            queue += i++ + " " + it.next().getInfo().title + "\n";
        }
        sendBeautifulMessage(event, queue);
    }

    private static void stop(MessageReceivedEvent event, String[] argStrings) {
        Guild guild = event.getGuild();

        if (guild != null) {
            player.stop(event.getTextChannel());
        }
    }

    public static void stats(MessageReceivedEvent event) {
        sendBeautifulMessage(event, MetaHandler.greet() + MetaHandler.runtime() + MetaHandler.helpMessage());
    }

    private static void play(MessageReceivedEvent event, String[] argStrings) {
        Guild guild = event.getGuild();

        if (guild != null) {
            if (argStrings.length >= 2) {
                String trackUrl = "ytsearch: ";
                for (int i = 1; i < argStrings.length; i++) {
                    trackUrl += argStrings[i] + " ";
                }
                AudioPlayer.connectToUserVoiceChannel(event.getGuild().getAudioManager(), event.getMember().getVoiceState().getChannel());
                player.loadAndPlay(event.getTextChannel(), trackUrl);
            }
        }
    }

    private static void skip(MessageReceivedEvent event, String[] argStrings) {
        Guild guild = event.getGuild();
        if (guild != null) {
            player.skipTrack(event.getTextChannel());
        }
    }

    private static void addYt(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings[1].equals("status")) {
            sendMessage(event, YoutubeWatcher.status());
        } else if (argStrings[1].equals("remove") || argStrings[1].equals("rm")) {
            try {
                String channelname = "";
                for (int i = 2; i < argStrings.length; i++) {
                    channelname += argStrings[i];
                    if (i < argStrings.length - 1)
                        channelname += " ";
                }
                YoutubeWatcher.remove(channelname);
                sendMessage(event, "Not watching " + channelname + " anymore.");
            } catch (Exception e) {
                sendMessage(event,
                        "Sorry, that didn't work! To remove a channel from being watched, please type: #yt remove <channel name>");
            }
        } else if (argStrings[1].equals("add")) {
            for (YoutubeXML urls : YoutubeWatcher.latestvideos.keySet()) {
                if (argStrings[2].equals(urls.url)) {
                    sendMessage(event, "this channel was already added");
                    return;
                }
            }
            YoutubeWatcher.update(argStrings[2]);
            for (YoutubeXML yt : YoutubeWatcher.latestvideos.keySet()) {
                if (yt.url.equals(argStrings[2])) {
                    sendMessage(event, "added channel to watch: " + yt.getChannelName());
                    return;
                }
            }
        } else {
            sendBeautifulMessage(event, "invalid argument. usage: \n #yt add <URL to channel> \n #yt remove/rm <channelname> \n #yt status \n");
        }

    }

    private static void calculate(MessageReceivedEvent event, String[] argStrings) {
        String term = "";
        for (int i = 1; i < argStrings.length; i++) {
            term += argStrings[i];
        }
        Calculator calculator = new Calculator();
        try {
            sendMessage(event, Double.toString(calculator.calculate(term)));
        } catch (DivisionByZero e) {
            sendMessage(event, "http://math-fail.com/images-old/divide-by-zero3.jpg");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sendMessage(event, "#calc usage:\n number (+ - * /) number");
        }
    }

    private static void clear(MessageReceivedEvent event, String[] argStrings) {
        int deleteMessageCount = Integer.parseInt(argStrings[1]);
        MessageHistory history = new MessageHistory(event.getChannel());
        List<Message> msgs;
        msgs = history.retrievePast(deleteMessageCount + 1).complete();
        event.getChannel().purgeMessages(msgs);
        sendMessage(event, "Deleted " + deleteMessageCount + " messages.");
    }

    private static void coffee(MessageReceivedEvent event, String[] argStrings) {
        sendMessage(event, getImage("https://nekobot.xyz/api/image?type=coffee"));
    }

    private static void gah(MessageReceivedEvent event, String[] argStrings) {
        sendMessage(event, getImage("https://nekobot.xyz/api/image?type=gah"));
    }

    private static void hentai(MessageReceivedEvent event, String[] argStrings) {
        sendMessage(event, getImage("https://nekobot.xyz/api/image?type=hentai"));
    }

    private static void neko(MessageReceivedEvent event, String[] argStrings) {
        sendMessage(event, getImage("https://nekobot.xyz/api/image?type=neko"));
    }

    private static void pgif(MessageReceivedEvent event, String[] argStrings) {
        sendMessage(event, getImage("https://nekobot.xyz/api/image?type=pgif"));
    }

    private static void thigh(MessageReceivedEvent event, String[] argStrings) {
        sendMessage(event, getImage("https://nekobot.xyz/api/image?type=thigh"));
    }

    private static void danbooru(MessageReceivedEvent event, String[] argStrings) {
        String URL = "https://danbooru.donmai.us/posts.json/?utf8=%E2%9C%93&limit=200&tags=";
        if (argStrings.length > 3)
            sendMessage(event, "`you cannot search for more than 2 tags!`");
        for (int i = 1; i < argStrings.length; i++) {
            argStrings[i] = argStrings[i].replace('-', '_');
            URL += argStrings[i] + "+";
        }
        if (argStrings.length == 2)
            sendMessage(event, getDanbooru(URL, argStrings[1]));
        if (argStrings.length == 3)
            sendMessage(event, getDanbooru(URL, argStrings[1], argStrings[2]));

    }

    private static void recipes(MessageReceivedEvent event, String[] argStrings) {
        String URL = "https://www.food2fork.com/api/search?key=ffb26f279a14aaf6b21493d4ebccb867&q=";
        for (int i = 1; i < argStrings.length; i++) {
            URL += argStrings[i] + "%20";
        }
        sendMessage(event, getRecipe(URL));
    }

    private static void help(MessageReceivedEvent event) {
        ArrayList<String> allowList = new ArrayList<String>();
        for (String command : permissions.keySet()) {
            if (isAllowed(event.getMember(), command))
                allowList.add(command);
        }

        String help = "usage:\n";
        for (String comm : allowList) {
            help += comm + "\n";
        }
        sendBeautifulMessage(event, help);
        ;
    }

    private static void sendMessage(MessageReceivedEvent event, String text) {
        event.getChannel().sendMessage(text).queue();
    }

    public static void sendMessage(TextChannel channel, String text) {
        channel.sendMessage(text).queue();
    }

    public static void sendBeautifulMessage(MessageReceivedEvent event, String text) {
        event.getChannel().sendMessage("```" + text + "```").queue();
    }

    private static String[] getArgs(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().split(" ");
    }

    private static java.net.URLConnection openConnection(String URL) throws IOException {
        URL url = new URL(URL);
        java.net.URLConnection request = url.openConnection();
        request.addRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        request.connect();
        return request;
    }

    private static JsonObject parseJson(String URL) {
        try {
            URLConnection request = openConnection(URL);
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            return root.getAsJsonObject();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JsonArray parseJsonArray(String URL) {
        try {
            java.net.URLConnection request = openConnection(URL);
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            return root.getAsJsonArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getImage(String URL) {
        return parseJson(URL).get("message").getAsString();
    }

    private static String getDanbooru(String URL, String tag1, String tag2) {
        JsonArray object = parseJsonArray(URL);
        if (object.size() == 0)
            return "no posts found, searching for matching tags\n" + tag1 + "\n" + getDanbooruTags(tag1) + "\n" + tag2
                    + "\n" + getDanbooruTags(tag2);
        return getRandomObject(object).get("file_url").getAsString();
    }

    private static JsonObject getRandomObject(JsonArray object) {
        Random random = new Random();
        int rand = random.nextInt(object.size());
        return (JsonObject) object.get(rand);
    }

    private static String getDanbooru(String URL, String tag1) {
        JsonArray object = parseJsonArray(URL);
        if (object.size() == 0)
            return "no posts found, searching for matching tags\n" + tag1 + "\n" + getDanbooruTags(tag1);
        return getRandomObject(object).get("file_url").getAsString();
    }

    private static String getDanbooruTags(String tag) {
        JsonArray object = parseJsonArray(
                "https://danbooru.donmai.us/tags.json/?search[hide_empty]=true&search[order]=count&search[name_matches]="
                        + "*" + tag + "*");
        String tags = "`";
        for (int i = 0; i < object.size() / 2; i++) {
            JsonObject element = (JsonObject) object.get(i);
            tags += element.get("name") + " post count: " + element.get("post_count") + "\n";
        }
        tags += "`";
        return tags;
    }

    private static String getRecipe(String URL) {
        JsonObject jObject = parseJson(URL);
        if (jObject.get("count").getAsInt() == 0) {
            return "no recipes found";
        }
        JsonArray object = (JsonArray) jObject.get("recipes");

        return getRandomObject(object).get("source_url").getAsString();
    }
}
