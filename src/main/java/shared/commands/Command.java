package shared.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import main.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {

    private List<String> commandPrefixes = new ArrayList<>();
    private List<String> permission = new ArrayList<>();
    private List<Command> subCommands = new ArrayList<>();
    private String description = "";
    private String helpString = "";
    private String topic = "uncategorized";
    private int minArguments = 0;

    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length < minArguments) {
            CommandManager.sendMessage(event, getHelp());
        } else {
            handleImpl(event, argStrings);
        }
    }

    public Command findSubCommand(String argument) {
        Command foundSubCommand = null;
        for (Command subCommand : this.subCommands) {
            if (subCommand.getCommand().contains(argument)) {
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
                .map(newCommandPrefix -> newCommandPrefix).collect(Collectors.toList());
        this.commandPrefixes.addAll(newCommandPrefixes);
    }

    public void addPermission(String permissionToAdd) {
        this.permission.add(permissionToAdd);
    }

    public void setCommand(String... cmd) {
        commandPrefixes = Arrays.asList(cmd).stream().map(commandPrefix -> commandPrefix).collect(Collectors.toList());
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

    protected abstract void handleImpl(MessageReceivedEvent event, String[] argStrings);

    public List<String> getPermissions() {
        return permission;
    }

    public int getMinArguments() {
        return minArguments;
    }

    public void setMinArguments(int minArguments) {
        this.minArguments = minArguments;
    }
}
