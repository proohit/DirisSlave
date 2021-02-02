package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class RepeatCommand extends Command {
    public RepeatCommand() {
        addPermission("everyone");
        addCommendPrefix("repeat");
        setDescription("enables queue repetition. Now playing song will repeat as the last song");
        setTopic("music");
        setHelpString("<true|false>");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 1) {
            String inputBoolean = argStrings[0];
            if (inputBoolean.equals("true") || inputBoolean.equals("false")) {
                boolean isRepeat = Boolean.parseBoolean(inputBoolean);
                Commands.sendBeautifulMessage(event, "Repetition of songs has been set to: "
                        + Commands.player.setRepeat(event.getTextChannel(), isRepeat));
            } else {
                Commands.sendMessage(event, getHelp());
            }
        } else {
            Commands.sendMessage(event, getHelp());
        }
    }
}
