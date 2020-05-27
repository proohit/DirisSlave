package util.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.util.List;

public class ClearCommand extends Command {
    public ClearCommand() {
        addPermission("Bananenchefs");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length < 1) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        int deleteMessageCount = Integer.parseInt(argStrings[0]);
        MessageHistory history = new MessageHistory(event.getChannel());
        List<Message> msgs;
        msgs = history.retrievePast(deleteMessageCount + 1).complete();
        event.getChannel().purgeMessages(msgs);
        main.Commands.sendMessage(event, "Deleted " + deleteMessageCount + " messages.");
    }

    @Override
    protected String defineCommand() {
        return prefix + "del";
    }

    @Override
    protected String defineDescription() {
        return "mass-delete messages from a channel";
    }

    @Override
    protected String defineTopic() {
        return "util";
    }

    @Override
    protected String defineHelpString() {
        return "<number of messages to delete>";
    }
}
