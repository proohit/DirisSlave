package main;

import audioplayer.*;
import audioplayer.commands.*;
import calculator.CalculatorCommand;
import imageboards.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;
import util.commands.ClearCommand;
import util.commands.HelpCommand;
import util.commands.RecipeCommand;
import util.commands.StatsCommand;
import youtubewatcher.commands.YtWatcherCommand;

import java.util.*;

public class Commands {

    //TODO ändern in HashMap<util.Command, String>
    public static ArrayList<util.Command> permissions = new ArrayList<>();
//    public static HashMap<util.Command, String> permissions = new HashMap<>();
    public static AudioPlayer player;

    PlayCommand playCommand = new PlayCommand();
    HistoryCommand historyCommand = new HistoryCommand();
    SkiptoCommand skiptoCommand = new SkiptoCommand();
    RemoveCommand removeCommand = new RemoveCommand();
    QueueCommand queueCommand = new QueueCommand();
    StopCommand stopCommand = new StopCommand();
    SkipCommand skipCommand = new SkipCommand();
    ShuffleCommand shuffleCommand = new ShuffleCommand();
    CalculatorCommand calculatorCommand = new CalculatorCommand();
    CoffeeCommand coffeeCommand = new CoffeeCommand();
    DanbooruCommand danbooruCommand = new DanbooruCommand();
    ThighCommand thighCommand = new ThighCommand();
    PgifCommand pgifCommand = new PgifCommand();
    NekoCommand nekoCommand = new NekoCommand();
    HentaiCommand hentaiCommand = new HentaiCommand();
    GahCommand gahCommand = new GahCommand();
    ClearCommand clearCommand = new ClearCommand();
    RecipeCommand recipeCommand = new RecipeCommand();
    HelpCommand helpCommand = new HelpCommand();
    StatsCommand statsCommand = new StatsCommand();
    YtWatcherCommand ytWatcherCommand = new YtWatcherCommand();
    PlaylistCommand playlistCommand = new PlaylistCommand();
    LizardCommand lizardCommand = new LizardCommand();
    SeekCommand seekCommand = new SeekCommand();
    JumptoCommand jumptoCommand = new JumptoCommand();
    public Commands() {
        player = new AudioPlayer();
        //TODO für jeden command ändern in put(command Objekt, command Objekt.getCommand())
//        permissions.put(prefix + "del", "Bananenchefs");
        permissions.add(playCommand);
        permissions.add(playlistCommand);
        permissions.add(historyCommand);
        permissions.add(skiptoCommand);
        permissions.add(removeCommand);
        permissions.add(queueCommand);
        permissions.add(stopCommand);
        permissions.add(shuffleCommand);
        permissions.add(seekCommand);
        permissions.add(jumptoCommand);
        permissions.add(ytWatcherCommand);
        permissions.add(skipCommand);
        permissions.add(calculatorCommand);
        permissions.add(coffeeCommand);
        permissions.add(danbooruCommand);
        permissions.add(thighCommand);
//        permissions.add(pgifCommand);
//        permissions.add(nekoCommand);
//        permissions.add(hentaiCommand);
        permissions.add(gahCommand);
        permissions.add(clearCommand);
        permissions.add(recipeCommand);
        permissions.add(helpCommand);
        permissions.add(statsCommand);
        permissions.add(lizardCommand);
    }

    public static boolean isAllowed(Member member, String inputCommand) {

        boolean isAllowed = false;
        Command currentCommand = permissions.stream().filter(command -> command.getCommand().equals(inputCommand)).findFirst().get();
        if(currentCommand.getPermission().equals("everyone")) return true;
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
            //TODO switch ersetzen durch filter auf zukünftige Map voller Commands -> wenn command gefunden, dann handle
            insertedCommand.handle(event, argStrings);
        } catch(NoSuchElementException e) {
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
