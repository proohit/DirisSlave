package util.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.List;

public class ClearCommand extends Command {
    public ClearCommand() {
        setCommand(prefix + "del");
        setPermission("Bananenchefs");
        setTopic("util");
        setDescription("mass-delete messages from a channel");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length < 2) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        int deleteMessageCount = Integer.parseInt(argStrings[1]);
        MessageHistory history = new MessageHistory(event.getChannel());
        List<Message> msgs;
        msgs = history.retrievePast(deleteMessageCount + 1).complete();
        event.getChannel().purgeMessages(msgs);
        main.Commands.sendMessage(event, "Deleted " + deleteMessageCount + " messages.");
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<number of messages to delete> \n");

        return help.toString();
    }
}
