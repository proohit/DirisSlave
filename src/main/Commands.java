package main;

import audioplayer.AudioPlayer;
import audioplayer.PlaylistManager;
import audioplayer.loadplaylistCommand;
import audioplayer.playCommand;
import calculator.Calculator;
import calculator.DivisionByZero;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import metahandler.MetaHandler;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import youtubewatcher.YoutubeWatcher;
import youtubewatcher.YoutubeXML;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class Commands {

    static final String prefix = "#";
    static HashMap<String, String> permissions = new HashMap<>();
    public static AudioPlayer player;
    playCommand play = new playCommand();
    audioplayer.loadplaylistCommand loadplaylistCommand = new loadplaylistCommand();
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
        permissions.put(prefix + "history", "Bananenchefs");
        permissions.put(prefix + "savehistory", "Bananenchefs");
        permissions.put(prefix + "loadplaylist", "Bananenchefs");
        permissions.put(prefix + "listplaylist", "Bananenchefs");
        permissions.put(prefix + "deleteplaylist", "Bananenchefs");
        permissions.put(prefix + "addtoplaylist", "Bananenchefs");

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
                play.handle(event, argStrings);
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
            case "#history":
                history(event, argStrings);
                break;
            case "#savehistory":
                savehistory(event, argStrings);
                break;
            case "#loadplaylist":
                loadplaylistCommand.handle(event, argStrings);
                break;
            case "#listplaylist":
                listplaylist(event, argStrings);
                break;
            case "#deleteplaylist":
                deleteplaylist(event, argStrings);
                break;
            case "#addtoplaylist":
                addtoplaylist(event, argStrings);
                break;
        }
    }

    private void addtoplaylist(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 3) {
            String search = "ytsearch: ";
            for (int i = 2; i < argStrings.length; i++) {
                search += argStrings[i] + " ";
            }
            player.fetchAudioTrack(event.getTextChannel(), search, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    if (PlaylistManager.addToPlaylist(argStrings[1], track)) {
                        sendBeautifulMessage(event, "added \"" + track.getInfo().title + "\" to playlist " + argStrings[1]);
                    } else {
                        sendBeautifulMessage(event, "there is no playlist " + argStrings[1]);
                    }
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    AudioTrack firstTrack = playlist.getTracks().get(0);
                    if (PlaylistManager.addToPlaylist(argStrings[1], firstTrack)) {
                        sendBeautifulMessage(event, "added \"" + firstTrack.getInfo().title + "\" to playlist " + argStrings[1]);
                    } else {
                        sendBeautifulMessage(event, "there is no playlist " + argStrings[1]);
                    }
                }

                @Override
                public void noMatches() {
                    sendBeautifulMessage(event, "Nothing found for keywords");
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    System.out.println("loadFailed");
                }
            });
        }
    }

    private void deleteplaylist(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 2) {
            String result;
            result = PlaylistManager.deletePlaylist(argStrings[1]) ? "deleted playlist " + argStrings[1] : "playlist " + argStrings[1] + " not found";
            sendBeautifulMessage(event, result);
        }
    }

    private void listplaylist(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            ArrayList<String> playlists = PlaylistManager.getPlaylists();
            if (playlists.size() == 0) {
                sendBeautifulMessage(event, "no playlists found");
                return;
            }
            StringBuilder result = new StringBuilder("saved playlists: \n");
            for (String playlist : playlists) {
                result.append(playlist).append("\n");
            }
            sendBeautifulMessage(event, result.toString());
        } else if (argStrings.length == 2) {
            ArrayList<PlaylistManager.Song> playlist = PlaylistManager.getSongsOfPlaylist(argStrings[1]);
            if (playlist.size() == 0) {
                sendBeautifulMessage(event, "no such playlist or no songs found for " + argStrings[1]);
                return;
            }
            StringBuilder result = new StringBuilder("songs of playlist " + argStrings[1] + ":\n");
            for (PlaylistManager.Song song : playlist) {
                result.append(song.getTitle()).append("\n");
            }
            sendBeautifulMessage(event, result.toString());
        }
    }

    private void loadplaylist(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 2) {
            PlaylistManager.loadPlaylist(argStrings[1], event);
        }
    }

    private void savehistory(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 2) {
            PlaylistManager.savePlaylistFromHistory(argStrings[1]);
            sendBeautifulMessage(event, "saved playlist " + argStrings[1]);
        }
    }

    private static void history(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            StringBuilder result = new StringBuilder("History: \n");
            ArrayList<String> history = PlaylistManager.getHistory();
            int i;
            if (history.size() < 10) {
                i = 1;
            } else {
                i = history.size() - 9;
            }
            for (; i <= history.size(); i++) {
                result.append(i).append(" ").append(history.get(i - 1)).append("\n");
            }
            sendBeautifulMessage(event, result.toString());
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
        StringBuilder queue = new StringBuilder("current queue: \n");
        Iterator<AudioTrack> it = player.getQueue(event.getTextChannel()).iterator();
        int i = 1;
        while (it.hasNext()) {
            queue.append(i++).append(" ").append(it.next().getInfo().title).append("\n");
        }
        sendBeautifulMessage(event, queue.toString());
    }

    private static void stop(MessageReceivedEvent event, String[] argStrings) {
        AudioManager manager = event.getGuild().getAudioManager();
        if(manager.isConnected() || manager.isAttemptingToConnect()) manager.closeAudioConnection();
        player.stop(event.getTextChannel());
    }

    public static void stats(MessageReceivedEvent event) {
        sendBeautifulMessage(event, MetaHandler.greet() + MetaHandler.runtime() + MetaHandler.helpMessage());
    }

    private static void skip(MessageReceivedEvent event, String[] argStrings) {
        player.skipTrack(event.getTextChannel());
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
                if (argStrings[2].equals(urls.getUrl())) {
                    sendMessage(event, "this channel was already added");
                    return;
                }
            }
            YoutubeWatcher.update(argStrings[2]);
            for (YoutubeXML yt : YoutubeWatcher.latestvideos.keySet()) {
                if (yt.getUrl().equals(argStrings[2])) {
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
        ArrayList<String> allowList = new ArrayList<>();
        for (String command : permissions.keySet()) {
            if (isAllowed(event.getMember(), command))
                allowList.add(command);
        }
        Collections.sort(allowList);
        String help = "usage:\n";
        for (String comm : allowList) {
            help += comm + "\n";
        }
        sendBeautifulMessage(event, help);
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
