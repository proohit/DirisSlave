package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import dice.commands.DiceRollCommand;
import imageboards.commands.CoffeeCommand;
import imageboards.commands.DanbooruCommand;
import imageboards.commands.GahCommand;
import imageboards.commands.LizardCommand;
import imageboards.commands.PixabayCommand;
import imageboards.commands.ThighCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import shared.commands.Command;
import shared.commands.SlashCommand;
import util.commands.ChangePrefixCommand;
import util.commands.ClearCommand;
import util.commands.HelpCommand;
import util.commands.SetMusicChannelCommand;
import weather.commands.WeatherCommand;

public class CommandManager {

    public static final List<Command> registeredCommands = new ArrayList<>();
    public static final List<SlashCommand> slashCommands = new ArrayList<>();
    public static final PermissionManager permissionManager = new PermissionManager(registeredCommands);
    public static final ConfigurationManager CONFIGURATION_MANAGER = new ConfigurationManager();
    public static final AudioPlayer player = new AudioPlayer();

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
        registerCommand(new PlayCommand());
        registeredCommands.add(new ThighCommand());
        registeredCommands.add(new GahCommand());
        registeredCommands.add(new CoffeeCommand());
        registeredCommands.add(new WeatherCommand());
        registeredCommands.add(new LizardCommand());
        registeredCommands.add(new PixabayCommand());
        registeredCommands.add(new DiceRollCommand());
        registeredCommands.add(new ChangePrefixCommand());
        registeredCommands.add(new SetMusicChannelCommand());
        registeredCommands.add(HelpCommand.getInstance());
    }

    public void handle(MessageReceivedEvent event) {
        String[] argStrings = getArgs(event);

        String prefixOfGuild = CONFIGURATION_MANAGER.getPrefixForGuild(event.getGuild().getIdLong());

        if (!argStrings[0].startsWith(prefixOfGuild)) {
            return;
        }

        Command insertedCommand = getRequestedCommand(argStrings, prefixOfGuild);

        if (insertedCommand == null) {
            return;
        }

        if (insertedCommand.getTopic().equals("music") && !CONFIGURATION_MANAGER
                .isMusicchannelOfGuild(event.getGuild().getIdLong(), event.getChannel().getName())) {
            MessageUtils.sendBeautifulMessage(event,
                    String.format("Please input your request in the music channel \"%s\"",
                            CONFIGURATION_MANAGER.getMusicchannelForGuild(event.getGuild().getIdLong())));
            return;
        }

        String[] splitArguments = splitArgumentsForCommand(argStrings, insertedCommand, prefixOfGuild);

        if (permissionManager.isPermitted(event.getMember(), insertedCommand)) {
            insertedCommand.handle(event, splitArguments);
        }
    }

    public void handle(SlashCommandInteractionEvent event) {
        SlashCommand command = getRequestedCommand(event.getName());
        if (command == null) {
            return;
        }
        event.deferReply().queue();
        command.handle(event);
    }

    private String[] splitArgumentsForCommand(String[] argStrings, Command insertedCommand, String prefix) {
        int indexOfInsertedCommand = 0;
        for (; indexOfInsertedCommand < argStrings.length; indexOfInsertedCommand++) {
            if (insertedCommand.getCommand().contains(argStrings[indexOfInsertedCommand].replace(prefix, ""))) {
                break;
            }
        }
        if (argStrings.length < indexOfInsertedCommand + 1) {
            return new String[] {};
        } else {
            return Arrays.copyOfRange(argStrings, indexOfInsertedCommand + 1, argStrings.length);
        }
    }

    private Command getRequestedCommand(String[] arguments, String prefix) {
        final String pureCommand = arguments[0].replace(prefix, "");
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

    private SlashCommand getRequestedCommand(String name) {
        return slashCommands.stream().filter(command -> command.getNames().contains(name)).findFirst()
                .orElse(null);
    }

    public static void registerCommand(Command commandToRegister) {
        registeredCommands.add(commandToRegister);
    }

    public static void registerCommand(SlashCommand commandToRegister) {
        slashCommands.add(commandToRegister);
        List<Guild> guilds = Startup.jda.getGuilds().stream()
                .collect(Collectors.toList());

        for (Guild guild : guilds) {
            SlashCommandData slashCommandData = Commands.slash(commandToRegister.getNames().get(0),
                    commandToRegister.getDescription());
            slashCommandData.addOptions(commandToRegister.getOptions());
            guild.updateCommands().addCommands(slashCommandData).queue();
        }
    }

    private static String[] getArgs(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().split(" ");
    }
}
