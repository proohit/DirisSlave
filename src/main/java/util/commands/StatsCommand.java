package util.commands;

import metahandler.MetaHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class StatsCommand extends Command {
    public StatsCommand() {
        addPermission("everyone");
        addCommendPrefix("stats");
        setDescription("see the running-time");
        setTopic("util");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        main.CommandManager.sendBeautifulMessage(event,
                MetaHandler.greet() + MetaHandler.runtime() + MetaHandler.helpMessage());
    }

}
