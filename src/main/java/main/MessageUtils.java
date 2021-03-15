package main;

import java.util.function.Consumer;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageUtils {

    private MessageUtils() {
    }

    public static void sendMessage(MessageReceivedEvent event, String text) {
        event.getChannel().sendMessage(text).queue();
    }

    public static void sendMessage(MessageReceivedEvent event, String text, Consumer<? super Message> callback) {
        event.getChannel().sendMessage(text).queue(callback);
    }

    public static void sendMessage(TextChannel channel, String text) {
        channel.sendMessage(text).queue();
    }

    public static void sendBeautifulMessage(MessageReceivedEvent event, String text) {
        event.getChannel().sendMessage("```" + text + "```").queue();
    }

    public static void sendBeautifulMessage(TextChannel channel, String text) {
        channel.sendMessage("```" + text + "```").queue();
    }

}
