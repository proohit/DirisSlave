package util.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.io.IOException;

public class RestartCommand extends Command {
    public RestartCommand() {
        setCommand(prefix + "restart");
        addPermission("everyone");
        setTopic("util");
        setDescription("restarts the bot on the server");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 1) {
            try {
                Process process = Runtime.getRuntime().exec("supervisorctl restart discordbot");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            main.Commands.sendMessage(event, getHelp());
            return;
        }
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        // TODO Auto-generated method stub

    }
}
