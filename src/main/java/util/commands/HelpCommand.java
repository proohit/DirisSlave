package util.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import main.Commands;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import shared.commands.Command;

public class HelpCommand extends Command {
    private int HELP_PER_PAGE = 5;
    private int currentPage = 0;
    private int lastPage;
    private int restCommands;
    private Message lastSentHelpMessage;
    private static HelpCommand instance;

    private HelpCommand() {
        addPermission("everyone");
        addCommendPrefix("help", "?");
        setDescription("lists options for tags [arguments]");
        setTopic("util");
        setHelpString("[<command to get help for>]");
    }

    public static HelpCommand getInstance() {
        if (instance == null)
            instance = new HelpCommand();

        return instance;
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        List<Command> allowedCommands = getAllAllowedCommands(event.getMember());
        if (argStrings.length >= 1) {
            Command requestedCommand = allowedCommands.stream().filter(registeredCommand -> registeredCommand
                    .getCommand().contains(registeredCommand.getPrefix() + argStrings[0])).findFirst().orElse(null);
            if (requestedCommand == null) {
                return;
            }
            for (int subCommandIterator = 1; !requestedCommand.getSubCommands().isEmpty()
                    && subCommandIterator < argStrings.length; subCommandIterator++) {
                Command tmpCommand = requestedCommand;
                requestedCommand = requestedCommand.findSubCommand(argStrings[subCommandIterator]);
                if (requestedCommand == null) {
                    requestedCommand = tmpCommand;
                }
            }
            Commands.sendMessage(event, requestedCommand.getHelp());
        } else {
            currentPage = 0;
            lastPage = (allowedCommands.size() / HELP_PER_PAGE) - 1;
            restCommands = allowedCommands.size() % HELP_PER_PAGE;
            if (restCommands > 0)
                lastPage++;
            String helpString = buildHelpString(event.getAuthor(), allowedCommands);
            Commands.sendMessage(event, helpString, new MessageSentHandler());
        }
    }

    private String buildHelpString(User author, List<Command> allowedCommands) {
        StringBuilder helpString = new StringBuilder();
        helpString.append("__**Welcome, master ");
        helpString.append(author.getName());
        helpString.append("!**__\n\n");
        helpString.append(getHelpForPage(currentPage, allowedCommands));
        helpString.append("\n\n current page: ").append(currentPage).append("/").append(lastPage);
        return helpString.toString();
    }

    /**
     * returns the commands for the requested page. If the last page is requested,
     * only the last commands are returned
     * 
     * @param page
     * @param commands
     * @return
     */
    private List<Command> getPageCommands(int page, List<Command> commands) {
        List<Command> pageCommands = new ArrayList<>();
        int commandIndex;
        int iterateTo;
        if (page >= lastPage && restCommands > 0) {
            commandIndex = commands.size() - restCommands;
            iterateTo = commands.size();
        } else {
            commandIndex = page * HELP_PER_PAGE;
            iterateTo = (page + 1) * HELP_PER_PAGE;
        }
        for (; commandIndex < iterateTo; commandIndex++) {
            pageCommands.add(commands.get(commandIndex));
        }

        return pageCommands;
    }

    private List<Command> getAllAllowedCommands(Member member) {
        return Commands.registeredCommands.stream().filter(command -> command.isAllowed(member))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 
     * @param page     index of page to get. Begins at 0
     * @param commands
     * @return
     */
    public String getHelpForPage(int page, List<Command> commands) {
        if (commands.isEmpty()) {
            return "You are not permitted to perform any commands... Please contact the administrator";
        }

        StringBuilder pageHelpString = new StringBuilder();
        List<Command> pageCommands = getPageCommands(page, commands);
        Map<String, List<Command>> topics = pageCommands.stream().collect(Collectors.groupingBy(Command::getTopic));
        topics.keySet().stream().forEach(topic -> {
            pageHelpString.append("***" + topic + "*** functions" + "\n");
            topics.get(topic).stream().forEach(command -> {
                pageHelpString.append("\t");
                pageHelpString.append(command.getCommandHelpString());
                pageHelpString.append(" - ");
                pageHelpString.append(command.getDescription());
                pageHelpString.append("\n");
            });
        });
        pageHelpString.append(
                "\n If you find any bugs or have feature suggestions, pm me or send an email to direnc99@gmail.com. I will look into it A$AP.");
        return pageHelpString.toString();

    }

    public void handlePageRequest(GenericMessageReactionEvent event) {
        if (lastSentHelpMessage == null) {
            return;
        }
        if (lastSentHelpMessage.getId().equals(event.getMessageId())) {
            String ARROW_LEFT = "U+25c0U+fe0f";
            String ARROW_RIGHT = "U+25b6U+fe0f";
            String emoji = event.getReactionEmote().getAsCodepoints();
            if (emoji.equals(ARROW_LEFT)) {
                currentPage = currentPage - 1 < 0 ? lastPage : currentPage - 1;
            } else if (emoji.equals(ARROW_RIGHT)) {
                currentPage = currentPage + 1 > lastPage ? 0 : currentPage + 1;
            }
            List<Command> allAllowedCommands = getAllAllowedCommands(event.getMember());
            lastSentHelpMessage.editMessage(buildHelpString(event.getUser(), allAllowedCommands)).queue();
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

    public Message getLastSentHelpMessage() {
        return lastSentHelpMessage;
    }

    public void setLastSentHelpMessage(Message lastSentHelpMessage) {
        this.lastSentHelpMessage = lastSentHelpMessage;
    }

}
