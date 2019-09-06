package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

import static main.Commands.sendBeautifulMessage;

public class SkiptoCommand extends Command {
    public SkiptoCommand() {
        setCommand(prefix+"skipto");
        setPermission("Bananenchefs");
        setTopic("music");
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 2) {
            try {
                Commands.player.skipTo(Integer.parseInt(argStrings[1]), event.getTextChannel());
            } catch (NumberFormatException e) {
                sendBeautifulMessage(event, "the position you have entered is invalid");

            }
        }
    }
}
