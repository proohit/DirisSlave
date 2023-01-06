package bot.util.commands;

import bot.main.MessageUtils;
import bot.metahandler.MetaHandler;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StatsCommand extends Command {
    public StatsCommand() {
        addPermission("everyone");
        addCommendPrefix("stats");
        setDescription("see the running-time");
        setTopic("util");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        MessageUtils.sendBeautifulMessage(event,
                MetaHandler.greet() + MetaHandler.runtime() + MetaHandler.helpMessage());
    }

}
