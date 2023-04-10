package bot.main;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import bot.audioplayer.AudioPlayer;
import bot.audioplayer.commands.HistoryCommand;
import bot.audioplayer.commands.JumptoCommand;
import bot.audioplayer.commands.PauseCommand;
import bot.audioplayer.commands.QueueCommand;
import bot.audioplayer.commands.RecommendationCommand;
import bot.audioplayer.commands.RemoveCommand;
import bot.audioplayer.commands.RepeatCommand;
import bot.audioplayer.commands.ResumeCommand;
import bot.audioplayer.commands.SeekCommand;
import bot.audioplayer.commands.ShuffleCommand;
import bot.audioplayer.commands.SkipCommand;
import bot.audioplayer.commands.SkiptoCommand;
import bot.audioplayer.commands.SongStatisticsCommand;
import bot.audioplayer.commands.StopCommand;
import bot.audioplayer.commands.play.PlayCommand;
import bot.audioplayer.commands.playlist.PlaylistCommand;
import bot.calculator.commands.CalculatorCommand;
import bot.imageboards.commands.CoffeeCommand;
import bot.imageboards.commands.DanbooruCommand;
import bot.imageboards.commands.GahCommand;
import bot.imageboards.commands.LizardCommand;
import bot.imageboards.commands.ThighCommand;
import bot.shared.commands.Command;
import bot.util.commands.ClearCommand;
import bot.util.commands.HelpCommand;
import bot.weather.commands.WeatherCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Commands {

    public static final List<Command> registeredCommands = new ArrayList<>();
    public static final AudioPlayer player = new AudioPlayer();
    public static final String PREFIX = "-";

    public Commands() {
        registeredCommands.add(new PlaylistCommand());
        registeredCommands.add(new CalculatorCommand());
        registeredCommands.add(new HistoryCommand());
        registeredCommands.add(new JumptoCommand());
        registeredCommands.add(new PauseCommand());
        registeredCommands.add(new QueueCommand());
        registeredCommands.add(new RecommendationCommand());
        registeredCommands.add(new RemoveCommand());
        registeredCommands.add(new RepeatCommand());
        registeredCommands.add(new ResumeCommand());
        registeredCommands.add(new SeekCommand());
        registeredCommands.add(new ShuffleCommand());
        registeredCommands.add(new SkipCommand());
        registeredCommands.add(new SkiptoCommand());
        registeredCommands.add(new SongStatisticsCommand());
        registeredCommands.add(new StopCommand());
        registeredCommands.add(new DanbooruCommand());
        registeredCommands.add(new ClearCommand());
        registeredCommands.add(new PlayCommand());
        registeredCommands.add(new ThighCommand());
        registeredCommands.add(new GahCommand());
        registeredCommands.add(new CoffeeCommand());
        registeredCommands.add(new WeatherCommand());
        registeredCommands.add(new LizardCommand());
        registeredCommands.add(HelpCommand.getInstance());
    }

    public void handle(MessageReceivedEvent event) {
        String[] argStrings = getArgs(event);
        if (!argStrings[0].startsWith(PREFIX)) {
            return;
        }
        final String pureCommand = argStrings[0].replace(PREFIX, "");
        Command insertedCommand = registeredCommands.stream()
                .filter(command -> command.getCommand().contains(pureCommand)).findFirst().orElse(null);
        if (insertedCommand == null)
            return;
        insertedCommand.handle(event, argStrings);
    }

    public static void registerCommand(Command commandToRegister) {
        registeredCommands.add(commandToRegister);
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

    public static void sendBeautifulMessage(MessageChannel channel, String text) {
        channel.sendMessage("```" + text + "```").queue();
    }

    private static String[] getArgs(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().split(" ");
    }
}
