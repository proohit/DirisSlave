package main;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import audioplayer.AudioPlayer;
import audioplayer.commands.HistoryCommand;
import audioplayer.commands.JumptoCommand;
import audioplayer.commands.PauseCommand;
import audioplayer.commands.QueueCommand;
import audioplayer.commands.RecommendationCommand;
import audioplayer.commands.RemoveCommand;
import audioplayer.commands.RepeatCommand;
import audioplayer.commands.ResumeCommand;
import audioplayer.commands.SeekCommand;
import audioplayer.commands.ShuffleCommand;
import audioplayer.commands.SkipCommand;
import audioplayer.commands.SkiptoCommand;
import audioplayer.commands.SongStatisticsCommand;
import audioplayer.commands.StopCommand;
import audioplayer.commands.play.PlayCommand;
import audioplayer.commands.playlist.PlaylistCommand;
import calculator.CalculatorCommand;
import imageboards.DanbooruCommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.commands.ClearCommand;

public class Commands {

    // TODO ändern in HashMap<util.Command, String>
    public static ArrayList<util.Command> permissions = new ArrayList<>();
    // public static HashMap<util.Command, String> permissions = new HashMap<>();
    public static AudioPlayer player;

    public Commands() {
        player = new AudioPlayer();

        // TODO für jeden command ändern in put(command Objekt, command
        // Objekt.getCommand())
        permissions.add(new PlaylistCommand());
        permissions.add(new CalculatorCommand());
        permissions.add(new HistoryCommand());
        permissions.add(new JumptoCommand());
        permissions.add(new PauseCommand());
        permissions.add(new QueueCommand());
        permissions.add(new RecommendationCommand());
        permissions.add(new RemoveCommand());
        permissions.add(new RepeatCommand());
        permissions.add(new ResumeCommand());
        permissions.add(new SeekCommand());
        permissions.add(new ShuffleCommand());
        permissions.add(new SkipCommand());
        permissions.add(new SkiptoCommand());
        permissions.add(new SongStatisticsCommand());
        permissions.add(new StopCommand());
        permissions.add(new DanbooruCommand());
        permissions.add(new ClearCommand());
        permissions.add(new PlayCommand());
    }

    public void handle(MessageReceivedEvent event) {
        String[] argStrings = getArgs(event);
        // find the command that equals the input string
        try {
            Command insertedCommand = permissions.stream().filter(command -> {
                return command.getCommand().equals(argStrings[0]);
            }).findFirst().get();
            // when there is no command that suits the input
            if (insertedCommand == null)
                return;
            insertedCommand.handle(event, argStrings);
        } catch (NoSuchElementException e) {
        }
    }

    public static void sendMessage(MessageReceivedEvent event, String text) {
        event.getChannel().sendMessage(text).queue();
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

    private static String[] getArgs(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().split(" ");
    }
}
