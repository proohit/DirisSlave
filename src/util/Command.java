package util;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {
    protected String prefix = "#";
    String command;
    String description;
    String permission;
    public void setPermission(String perm) {permission = perm;}
    public String getPermission() { return permission; }
    public void setCommand(String cmd) {command = cmd;};
    public String getCommand() {return command;};
    public String getDescription() {
        return description;
    };
    public abstract void handle(MessageReceivedEvent event, String[] argStrings);
}
