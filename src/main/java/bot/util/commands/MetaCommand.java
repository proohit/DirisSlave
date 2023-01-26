package bot.util.commands;

import bot.main.MessageUtils;
import bot.metahandler.MetaHandler;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MetaCommand extends Command {
    public MetaCommand() {
        addPermission("everyone");
        addCommendPrefix("meta");
        setDescription("see meta information about the bot");
        setTopic("util");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        MessageUtils.sendBeautifulMessage(event,
                MetaHandler.getMetaInformation(event));
    }

}
