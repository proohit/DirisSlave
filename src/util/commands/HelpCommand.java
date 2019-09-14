package util.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.*;
import java.util.stream.Collectors;

public class HelpCommand extends Command {
    public HelpCommand() {
        setCommand(prefix + "help");
        setPermission("everyone");
        setTopic("util");
        setDescription("lists options for tags [arguments]");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        final StringBuilder help = new StringBuilder();

        if (argStrings.length == 2) {
            ArrayList<Command> allowList = new ArrayList<>();
            Commands.permissions.stream().filter(command -> Commands.isAllowed(event.getMember(), command.getCommand())).forEach(command -> allowList.add(command));
            try {
                Command requestedCommand = allowList.stream().filter(command -> command.getCommand().equals(prefix + argStrings[1])).findFirst().get();
                help.append(requestedCommand.getHelp());
            } catch (NoSuchElementException e) {

            }
        } else {
            ArrayList<Command> allowList = new ArrayList<>();
            Commands.permissions.stream().filter(command -> Commands.isAllowed(event.getMember(), command.getCommand())).forEach(command -> allowList.add(command));
            Map<String, List<Command>> topics = allowList.stream().collect(Collectors.groupingBy(Command::getTopic));

            help.append("__**Welcome, master ");
            help.append(event.getAuthor().getName());
            help.append("!**__\n\n");
            topics.keySet().stream().forEach(topic -> {
                help.append("***" + topic + "*** functions" + "\n");
                topics.get(topic).stream().forEach(command -> help.append("\t" + command.getCommand() + " - " + command.getDescription() + "\n"));
            });
        }
        main.Commands.sendMessage(event, help.toString());
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<command to get help for>");

        return help.toString();
    }
}
