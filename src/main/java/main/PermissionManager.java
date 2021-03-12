package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import shared.commands.Command;

public class PermissionManager {
    List<Command> commands;

    PermissionManager(List<Command> commands) {
        this.commands = commands;
    }

    public boolean isPermitted(Member member, Command insertedCommand) {
        if (insertedCommand.getPermissions().contains("everyone"))
            return true;
        for (Role role : member.getRoles()) {
            if (insertedCommand.getPermissions().contains(role.getName()))
                return true;
        }
        return false;
    }

    public List<Command> getPermittedCommands(Member member) {
        return commands.stream().filter(command -> isPermitted(member, command))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
