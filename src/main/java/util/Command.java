package util;

import java.util.ArrayList;
import java.util.Arrays;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public abstract class Command {
    protected String prefix = ".";
    String command;
    String description;
    String permission;
    String helpString;
    ArrayList<Command> subCommands = new ArrayList<>();

    String topic;

    public void setPermission(String perm) {
        permission = perm;
    }

    public String getPermission() {
        return permission;
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
        if (isAllowed(event.getMember())) {
            handleImpl(event, cutArguments(argStrings, 1, argStrings.length));
        }
    };

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
        if (this.getPermission().equals("everyone"))
            return true;
        for (Role role : member.getRoles()) {
            if (role.getName().equals(this.getPermission()))
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
