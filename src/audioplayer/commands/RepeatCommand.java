package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class RepeatCommand extends Command {
    public RepeatCommand() {
        setCommand(prefix + "repeat");
        setPermission("everyone");
        setTopic("music");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        boolean isRepeat;
        if (argStrings.length == 2) {
            if (argStrings[1].equals("true") || argStrings[1].equals("false")) {
                isRepeat = Boolean.parseBoolean(argStrings[1]);
                main.Commands.sendBeautifulMessage(event, "Repetition of songs has been set to: " + Commands.player.setRepeat(event.getTextChannel(), isRepeat));
            } else {
                main.Commands.sendMessage(event, getHelp());
            }
        }
    }

    @Override
    public String getHelp() {
        StringBuilder help = new StringBuilder();

        help.append("***" + getCommand() + "***");
        help.append(" - " + getDescription() + "\n");

        help.append("<true|false>\n");

        return help.toString();
    }
}
