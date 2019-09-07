package util.commands;

import metahandler.MetaHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class StatsCommand extends Command {
    public StatsCommand() {
        setCommand(prefix+"stats");
        setPermission("everyone");
        setTopic("util");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        main.Commands.sendBeautifulMessage(event, MetaHandler.greet() + MetaHandler.runtime() + MetaHandler.helpMessage());

    }
}
