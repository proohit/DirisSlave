package util.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import java.io.IOException;

public class RestartCommand extends Command {
    public RestartCommand() {
        setCommand(prefix + "restart");
        setPermission("everyone");
        setTopic("util");
        setDescription("restarts the bot on the server");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 1) {
            try {
                Process process = Runtime.getRuntime().exec("supervisorctl restart discordbot");

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
}
