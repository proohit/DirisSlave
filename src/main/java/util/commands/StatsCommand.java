package util.commands;

import metahandler.MetaHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class StatsCommand extends Command {
    public StatsCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        main.Commands.sendBeautifulMessage(event,
                MetaHandler.greet() + MetaHandler.runtime() + MetaHandler.helpMessage());
    }

    @Override
    protected String[] defineCommand() {
        return new String[] { "stats" };
    }

    @Override
    protected String defineDescription() {
        return "see the running-time";
    }

    @Override
    protected String defineTopic() {
        return "util";
    }

    @Override
    protected String defineHelpString() {
        return "";
    }
}
