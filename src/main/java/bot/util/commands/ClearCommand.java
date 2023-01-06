package bot.util.commands;

import java.util.List;

import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ClearCommand extends Command {
    public ClearCommand() {
        addPermission("Bananenchefs");
        addCommendPrefix("del", "clear");
        setDescription("mass-delete messages from a channel");
        setTopic("util");
        setHelpString("<number of messages to delete>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        int deleteMessageCount = Integer.parseInt(argStrings[0]);
        MessageHistory history = new MessageHistory(event.getChannel());
        List<Message> msgs = history.retrievePast(deleteMessageCount + 1).complete();
        event.getChannel().purgeMessages(msgs);
        MessageUtils.sendMessage(event, "Deleted " + deleteMessageCount + " messages.");
    }

}
