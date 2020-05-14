package util.commands;

import metahandler.MetaHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class StatsCommand extends Command {
    public StatsCommand() {
        setCommand(prefix + "stats");
        addPermission("everyone");
        setTopic("util");
        setDescription("see the running-time");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        main.Commands.sendBeautifulMessage(event, MetaHandler.greet() + MetaHandler.runtime() + MetaHandler.helpMessage());

    }


    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        return help.toString();
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        // TODO Auto-generated method stub

    }
}
