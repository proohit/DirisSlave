package util.commands;

import main.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class ChangePrefixCommand extends Command {
    public ChangePrefixCommand() {
        addPermission("Bananenchefs");
        addCommendPrefix("prefix");
        setTopic("util");
        setDescription("Changes the command prefix");
        setHelpString("<string of the new prefix>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        CommandManager.setPrefix(argStrings[0]);
        CommandManager.sendBeautifulMessage(event, String.format("Changed prefix to %s", CommandManager.getPrefix()));
    }
}
