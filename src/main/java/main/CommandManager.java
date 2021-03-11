package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

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
import calculator.commands.CalculatorCommand;
import imageboards.commands.CoffeeCommand;
import imageboards.commands.DanbooruCommand;
import imageboards.commands.GahCommand;
import imageboards.commands.LizardCommand;
import imageboards.commands.ThighCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;
import util.commands.ClearCommand;
import util.commands.HelpCommand;
import weather.commands.WeatherCommand;

public class CommandManager {

    public static final List<Command> registeredCommands = new ArrayList<>();
    public static final PermissionManager permissionManager = new PermissionManager(registeredCommands);
    public static final AudioPlayer player = new AudioPlayer();
    public static final String PREFIX = "-";

    public CommandManager() {
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

        Command insertedCommand = getRequestedCommand(argStrings);

        if (insertedCommand == null) {
            return;
        }

        String[] splitArguments = splitArgumentsForCommand(argStrings, insertedCommand);

        if (permissionManager.isPermitted(event.getMember(), insertedCommand)) {
            insertedCommand.handle(event, splitArguments);
        }
    }

    private String[] splitArgumentsForCommand(String[] argStrings, Command insertedCommand) {
        int indexOfInsertedCommand = 0;
        for (; indexOfInsertedCommand < argStrings.length; indexOfInsertedCommand++) {
            if (insertedCommand.getCommand().contains(argStrings[indexOfInsertedCommand].replace(PREFIX, ""))) {
                break;
            }
        }
        if (argStrings.length < indexOfInsertedCommand + 1) {
            return new String[] {};
        } else {
            return Arrays.copyOfRange(argStrings, indexOfInsertedCommand + 1, argStrings.length);
        }
    }

    private Command getRequestedCommand(String[] arguments) {
        final String pureCommand = arguments[0].replace(PREFIX, "");
        Command insertedHighLevelCommand = registeredCommands.stream()
                .filter(command -> command.getCommand().contains(pureCommand)).findFirst().orElse(null);

        if (insertedHighLevelCommand == null) {
            return null;
        }

        if (arguments.length <= 1) {
            return insertedHighLevelCommand;
        }

        String[] followingArguments = Arrays.copyOfRange(arguments, 1, arguments.length);
        Command subCommand = insertedHighLevelCommand;
        for (String argument : followingArguments) {
            Command newSubCommand = subCommand.findSubCommand(argument);
            if (newSubCommand != null) {
                subCommand = newSubCommand;
            }
        }
        return subCommand;
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

    public static void sendBeautifulMessage(TextChannel channel, String text) {
        channel.sendMessage("```" + text + "```").queue();
    }

    private static String[] getArgs(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().split(" ");
    }
}
