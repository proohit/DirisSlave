package util;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {
    protected String prefix = "#";
    String command;
    String description;
    String permission;



    String topic;

    public void setPermission(String perm) {permission = perm;}
    public String getPermission() { return permission; }
    public void setCommand(String cmd) {command = cmd;};
    public String getCommand() {return command;};
    public String getDescription() {return description;};
    public String getTopic() {return topic;}
    public void setTopic(String topic) {this.topic = topic;}
    public abstract void handle(MessageReceivedEvent event, String[] argStrings);
    public abstract String getHelp();
}
