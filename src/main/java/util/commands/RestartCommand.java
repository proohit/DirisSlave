package util.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.io.IOException;

public class RestartCommand extends Command {
    public RestartCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        try {
            Runtime.getRuntime().exec("supervisorctl restart discordbot");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String defineCommand() {
        return prefix + "restart";
    }

    @Override
    protected String defineDescription() {
        return "restarts the bot on the server";
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
