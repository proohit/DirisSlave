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
    String command;
    String description;
    ArrayList<String> permission = new ArrayList<>();
    String helpString = "";
    ArrayList<Command> subCommands = new ArrayList<>();

    String topic;

    protected Command() {
        setCommand(defineCommand());
        setTopic(defineTopic());
        setDescription(defineDescription());
        setHelpString(defineHelpString());
    }

    public void addPermission(String permissionToAdd) {
        this.permission.add(permissionToAdd);
    }

    public List<String> getPermissions() {
        return this.permission;
    }

    public void setCommand(String cmd) {
        command = cmd;
    };

    public String getCommand() {
        return command;
    };

    protected abstract String defineCommand();

    public String getDescription() {
        return description;
    };

    public void setDescription(String description) {
        this.description = description;
    }

    protected abstract String defineDescription();

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    protected abstract String defineTopic();

    public void handle(MessageReceivedEvent event, String[] argStrings) {
        String[] cutArguments = cutArguments(argStrings, 1, argStrings.length);
        if (isAllowed(event.getMember())) {
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

    protected abstract void handleImpl(MessageReceivedEvent event, String[] argStrings);

    public String getHelp() {
        final StringBuilder helpString = new StringBuilder();

        helpString.append(this.helpString).append("\n\n");
        if (!this.subCommands.isEmpty()) {
            helpString.append("available subcommands:").append("\n");
        }
        this.subCommands.forEach(subCommand -> {
            helpString.append(subCommand.getCommand()).append(" - ").append(subCommand.getDescription()).append("\n");
        });

        return helpString.toString();
    }

    public String getHelpString() {
        return helpString;
    }

    public void setHelpString(String helpString) {
        this.helpString += "***" + getCommand() + "***";
        this.helpString += " - " + getDescription() + "\n";
        this.helpString += helpString;
    }

    protected abstract String defineHelpString();

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

    public List<Command> getAllowedCommands(Member member) {
        return this.subCommands.stream().filter(command -> command.isAllowed(member))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected String[] cutArguments(String[] args, int startIndex, int endIndex) {
        return Arrays.copyOfRange(args, startIndex, endIndex);
    }

    protected void addSubCommand(Command subCommand) {
        this.subCommands.add(subCommand);
    }
}
