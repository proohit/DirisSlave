package main;

import audioplayer.*;
import audioplayer.commands.*;
import calculator.CalculatorCommand;
import imageboards.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.commands.*;
import weatherservice.WeatherCommand;
import youtubewatcher.commands.YtWatcherCommand;

import java.util.*;

public class Commands {

    //TODO ändern in HashMap<util.Command, String>
    public static ArrayList<util.Command> permissions = new ArrayList<>();
    //    public static HashMap<util.Command, String> permissions = new HashMap<>();
    public static AudioPlayer player;

    public Commands() {
        player = new AudioPlayer();

        //TODO für jeden command ändern in put(command Objekt, command Objekt.getCommand())
        permissions.add(new PlayCommand());
        permissions.add(new PlaylistCommand());
        permissions.add(new HistoryCommand());
        permissions.add(new SkiptoCommand());
        permissions.add(new RemoveCommand());
        permissions.add(new QueueCommand());
        permissions.add(new StopCommand());
        permissions.add(new ShuffleCommand());
        permissions.add(new RepeatCommand());
        permissions.add(new SeekCommand());
        permissions.add(new JumptoCommand());
        permissions.add(new YtWatcherCommand());
        permissions.add(new SkipCommand());
        permissions.add(new CalculatorCommand());
        permissions.add(new CoffeeCommand());
        permissions.add(new DanbooruCommand());
        permissions.add(new ThighCommand());
//        permissions.add(pgifCommand);
//        permissions.add(nekoCommand);
//        permissions.add(hentaiCommand);
        permissions.add(new GahCommand());
        permissions.add(new ClearCommand());
        permissions.add(new RecipeCommand());
        permissions.add(new HelpCommand());
        permissions.add(new RestartCommand());
        permissions.add(new StatsCommand());
        permissions.add(new LizardCommand());
        permissions.add(new WeatherCommand());
        permissions.add(new PauseCommand());
        permissions.add(new ResumeCommand());


    }

    public static boolean isAllowed(Member member, String inputCommand) {

        boolean isAllowed = false;
        Command currentCommand = permissions.stream().filter(command -> command.getCommand().equals(inputCommand)).findFirst().get();
        if (currentCommand.getPermission().equals("everyone")) return true;
        for (Role role : member.getRoles()) {
            if (role.getName().equals(currentCommand.getPermission()))
                isAllowed = true;
        }
        return isAllowed;
    }

    public void handle(MessageReceivedEvent event) {
        String[] argStrings = getArgs(event);
        //find the command that equals the input string
        try {
            Command insertedCommand = permissions.stream().filter(command -> command.getCommand().equals(argStrings[0])).findFirst().get();
            //when there is no command that suits the input
            if (insertedCommand == null) return;
            //check if user is allowed to type command
            if (!isAllowed(event.getMember(), insertedCommand.getCommand()))
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
