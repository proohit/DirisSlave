package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public abstract class Command {
    protected String prefix = ".";
    String command;
    String description;
    ArrayList<String> permission = new ArrayList<>();
    String helpString;
    ArrayList<Command> subCommands = new ArrayList<>();

    String topic;

    public void addPermission(String permissionToAdd) {
        this.permission.add(permissionToAdd);
    }

    protected List<String> getPermissions() {
        return this.permission;
    }

    public void setCommand(String cmd) {
        command = cmd;
    };

    public String getCommand() {
        return command;
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

    public void handle(MessageReceivedEvent event, String[] argStrings) {
        String[] cutArguments = cutArguments(argStrings, 1, argStrings.length);
        if (isAllowed(event.getMember())) {
            if (cutArguments.length < 1) {
                handleImpl(event, cutArguments);
            }
            Command foundSubCommand = findSubCommand(cutArguments[0]);
            if (foundSubCommand == null) {
                handleImpl(event, cutArguments);
            } else {
                foundSubCommand.handle(event, cutArguments);
            }
        }
    }

    private Command findSubCommand(String argument) {
        Command foundSubCommand = null;
        for (Command subCommand : this.subCommands) {
            if (subCommand.getCommand().equals(argument)) {
                foundSubCommand = subCommand;
            }
        }
        return foundSubCommand;
    }

    protected abstract void handleImpl(MessageReceivedEvent event, String[] argStrings);

    public String getHelp() {
        final StringBuilder helpString = new StringBuilder();

        helpString.append(this.helpString).append("\n");

        this.subCommands.forEach(subCommand -> {
            helpString.append(subCommand.getHelp()).append("\n");
        });

        return helpString.toString();
    }

    public String getHelpString() {
        return helpString;
    }

    public void setHelpString(String helpString) {
        this.helpString = helpString;
    }

    public ArrayList<Command> getSubCommands() {
        return subCommands;
    }

    public void setSubCommands(ArrayList<Command> subCommands) {
        this.subCommands = subCommands;
    };

    public Boolean isAllowed(Member member) {
        if (this.getPermissions().contains("everyone"))
            return true;
        for (Role role : member.getRoles()) {
            if (this.getPermissions().contains(role.getName()))
                return true;
        }
        return false;
    }

    protected String[] cutArguments(String[] args, int startIndex, int endIndex) {
        return Arrays.copyOfRange(args, startIndex, endIndex);
    }

    protected void addSubCommand(Command subCommand) {
        this.subCommands.add(subCommand);
    }
}
