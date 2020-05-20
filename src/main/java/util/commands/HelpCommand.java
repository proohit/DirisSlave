package util.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import main.Commands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import util.Command;

public class HelpCommand extends Command {
    static int HELP_PER_PAGE = 5;
    public static int currentPage = 0;
    public static Message lastSentHelpMessage;

    public HelpCommand() {
        setCommand(prefix + "help");
        addPermission("everyone");
        setTopic("util");
        setDescription("lists options for tags [arguments]");
        // final StringBuilder help = new StringBuilder();

        // if (argStrings.length == 2) {
        // ArrayList<Command> allowList = new ArrayList<>();
        // Commands.permissions.stream().filter(command ->
        // Commands.isAllowed(event.getMember(), command.getCommand())).forEach(command
        // -> allowList.add(command));
        // try {
        // Command requestedCommand = allowList.stream().filter(command ->
        // command.getCommand().equals(prefix + argStrings[1])).findFirst().get();
        // help.append(requestedCommand.getHelp());
        // } catch (NoSuchElementException e) {

        // }
        // } else {
        // ArrayList<Command> allowList = new ArrayList<>();
        // Commands.permissions.stream().filter(command ->
        // Commands.isAllowed(event.getMember(), command.getCommand())).forEach(command
        // -> allowList.add(command));
        // Map<String, List<Command>> topics =
        // allowList.stream().collect(Collectors.groupingBy(Command::getTopic));

        // help.append("__**Welcome, master ");
        // help.append(event.getAuthor().getName());
        // help.append("!**__\n\n");
        // topics.keySet().stream().forEach(topic -> {
        // help.append("***" + topic + "*** functions" + "\n");
        // topics.get(topic).stream().forEach(command -> help.append("\t" +
        // command.getCommand() + " - " + command.getDescription() + "\n"));
        // });

        // help.append("\n If you find any bugs or have feature suggestions, pm me or
        // send an email to direnc99@gmail.com. I will look into it A$AP.");
        // }
        // main.Commands.sendMessage(event, help.toString());
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length > 1) {

        } else {
            ArrayList<Command> allowedCommands = Commands.registeredCommands.stream()
                    .filter(command -> command.isAllowed(event.getMember()))
                    .collect(Collectors.toCollection(ArrayList::new));
            StringBuilder helpString = new StringBuilder();

            helpString.append("__**Welcome, master ");
            helpString.append(event.getAuthor().getName());
            helpString.append("!**__\n\n");

            currentPage = 0;
            helpString.append(getHelpForPage(currentPage, allowedCommands));

            Commands.sendMessage(event, helpString.toString(), new MessageSentHandler());
        }
    }

    /**
     * 
     * @param page     index of page to get. Begins at 0
     * @param commands
     * @return
     */
    static String getHelpForPage(int page, List<Command> commands) {
        if (commands.size() <= 0) {
            return "You are not permitted to perform any commands... Please contact the administrator";
        }
        if (page * HELP_PER_PAGE > commands.size()) {
            return getHelpForPage(0, commands);
        } else {
            StringBuilder pageHelpString = new StringBuilder();
            List<Command> pageCommands = new ArrayList<>();
            for (int pageIndex = page * HELP_PER_PAGE; pageIndex < (page + 1) * HELP_PER_PAGE; pageIndex++) {
                pageCommands.add(commands.get(pageIndex));
            }
            Map<String, List<Command>> topics = pageCommands.stream().collect(Collectors.groupingBy(Command::getTopic));
            topics.keySet().stream().forEach(topic -> {
                pageHelpString.append("***" + topic + "*** functions" + "\n");
                topics.get(topic).stream().forEach(command -> pageHelpString.append("\t").append(command.getCommand())
                        .append(" - ").append(command.getDescription()).append("\n"));
            });
            return pageHelpString.toString();
        }
    }

    public static void handlePageRequest(MessageReactionAddEvent event) {
        if (lastSentHelpMessage == null) {
            return;
        }
        if (lastSentHelpMessage.getId().equals(event.getMessageId())) {
            String ARROW_LEFT = "U+25c0U+fe0f";
            String ARROW_RIGHT = "U+25b6U+fe0f";
            String emoji = event.getReactionEmote().getAsCodepoints();
            if (emoji.equals(ARROW_LEFT)) {
                currentPage = currentPage - 1 < 0 ? 0 : currentPage - 1;
            } else if (emoji.equals(ARROW_RIGHT)) {
                currentPage++;
            }
            lastSentHelpMessage.editMessage(getHelpForPage(currentPage, commands))
        }
    }

    private class MessageSentHandler implements Consumer<Message> {

        @Override
        public void accept(Message t) {
            // Here we could use pagination
            t.addReaction("U+25c0U+fe0f").queue();
            t.addReaction("U+25b6U+fe0f").queue();
            lastSentHelpMessage = t;
        }

    }

    public static Message getLastSentHelpMessage() {
        return lastSentHelpMessage;
    }

    public static void setLastSentHelpMessage(Message lastSentHelpMessage) {
        HelpCommand.lastSentHelpMessage = lastSentHelpMessage;
    }
}
