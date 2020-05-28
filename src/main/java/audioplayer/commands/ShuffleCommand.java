package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class ShuffleCommand extends Command {
    public ShuffleCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (Commands.player.shuffle(event.getTextChannel())) {
            Commands.sendMessage(event, "shuffled the music :man_dancing:");
        } else {
            Commands.sendBeautifulMessage(event, "queue is empty :( start rocking with " + prefix + "pl !");
        }
    }

    @Override
    protected String defineCommand() {
        return prefix + "shuffle";
    }

    @Override
    protected String defineDescription() {
        return "bring some spice into the queue by shuffling it :man_dancing:";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "";
    }
}
