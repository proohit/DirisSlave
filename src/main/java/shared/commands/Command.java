package shared.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {

    protected String prefix = ".";
    private List<String> commandPrefixes = new ArrayList<>();
    private List<String> permission = new ArrayList<>();
    private List<Command> subCommands = new ArrayList<>();
    private String description = "";
    private String helpString = "";
    private String topic = "uncategorized";

    public void handle(MessageReceivedEvent event, String[] argStrings) {
        String[] cutArguments = cutArguments(argStrings, 1, argStrings.length);
        if (Boolean.TRUE == isAllowed(event.getMember())) {
            if (cutArguments.length < 1) {
                handleImpl(event, cutArguments);
            } else {
                Command foundSubCommand = findSubCommand(cutArguments[0]);
                if (foundSubCommand == null) {
                    handleImpl(event, cutArguments);
                } else {
                    foundSubCommand.handle(event, cutArguments);
                }
            }
        }
    }

    public Command findSubCommand(String argument) {
        Command foundSubCommand = null;
        for (Command subCommand : this.subCommands) {
            if (subCommand.getCommand().contains(this.prefix + argument)) {
                foundSubCommand = subCommand;
            }
        }
        return foundSubCommand;
    }

    public String getHelp() {
        final StringBuilder finalHelpString = new StringBuilder();
        finalHelpString.append(this.helpString).append("\n\n");
        if (!this.subCommands.isEmpty()) {
            finalHelpString.append("available subcommands:").append("\n");
            this.subCommands.forEach(subCommand -> finalHelpString.append(subCommand.getCommandHelpString())
                    .append(" - ").append(subCommand.getDescription()).append("\n"));
        }

        return finalHelpString.toString();

    }

    public String getCommandHelpString() {
        return String.join(" | ", getCommand());
    }

    protected void setHelpString(String helpString) {
        this.helpString += "***" + getCommandHelpString() + "***";
        this.helpString += " - " + getDescription() + "\n";
        this.helpString += helpString;
    }

    public List<Command> getSubCommands() {
        return subCommands;
    }

    public void setSubCommands(List<Command> subCommands) {
        this.subCommands = subCommands;
    };

    protected void addSubCommand(Command subCommand) {
        this.subCommands.add(subCommand);
    }

    protected void addCommendPrefix(String... commandPrefix) {
        List<String> newCommandPrefixes = Arrays.asList(commandPrefix).stream()
                .map(newCommandPrefix -> prefix + newCommandPrefix).collect(Collectors.toList());
        this.commandPrefixes.addAll(newCommandPrefixes);
    }

    public void addPermission(String permissionToAdd) {
        this.permission.add(permissionToAdd);
    }

    public void setCommand(String... cmd) {
        commandPrefixes = Arrays.asList(cmd).stream().map(commandPrefix -> this.prefix + commandPrefix)
                .collect(Collectors.toList());
    };

    public List<String> getCommand() {
        return commandPrefixes;
    };

    public String getDescription() {
        return description;
    };

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean isAllowed(Member member) {
        if (this.permission.contains("everyone"))
            return true;
        for (Role role : member.getRoles()) {
            if (this.permission.contains(role.getName()))
                return true;
        }
        return false;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    private String[] cutArguments(String[] args, int startIndex, int endIndex) {
        return Arrays.copyOfRange(args, startIndex, endIndex);
    }

    protected abstract void handleImpl(MessageReceivedEvent event, String[] argStrings);
}
