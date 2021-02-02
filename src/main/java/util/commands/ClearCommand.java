package util.commands;

import java.util.List;

import main.Commands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class ClearCommand extends Command {
    public ClearCommand() {
        addPermission("Bananenchefs");
        addCommendPrefix("del", "clear");
        setDescription("mass-delete messages from a channel");
        setTopic("util");
        setHelpString("<number of messages to delete>");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length < 1) {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
        int deleteMessageCount = Integer.parseInt(argStrings[0]);
        MessageHistory history = new MessageHistory(event.getChannel());
        List<Message> msgs = history.retrievePast(deleteMessageCount + 1).complete();
        event.getChannel().purgeMessages(msgs);
        Commands.sendMessage(event, "Deleted " + deleteMessageCount + " messages.");
    }

}
