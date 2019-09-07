package util.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.*;
import java.util.stream.Collectors;

public class HelpCommand extends Command {
    public HelpCommand() {
        setCommand(prefix+"help");
        setPermission("everyone");
        setTopic("util");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        ArrayList<Command> allowList = new ArrayList<>();
        Commands.permissions.stream().filter(command -> Commands.isAllowed(event.getMember(), command.getCommand())).forEach(command -> allowList.add(command));
        Map<String, List<Command>> topics = allowList.stream().collect(Collectors.groupingBy(Command::getTopic));

        final StringBuilder help = new StringBuilder();
        help.append("__**Welcome, master ");
        help.append(event.getAuthor().getName());
        help.append(" !**__\n\n");
        topics.keySet().stream().forEach(topic -> {
            help.append("***" + topic + "*** functions" + "\n");
            topics.get(topic).stream().forEach(command -> help.append("\t" + command.getCommand() + " - " + command.getDescription() + "\n"));
        });
//        for (Command comm : allowList) {
//            help += comm.getCommand() + "\n";
//        }
        main.Commands.sendMessage(event, help.toString());
    }
}
