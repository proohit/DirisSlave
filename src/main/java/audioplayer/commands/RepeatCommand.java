package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Command;

public class RepeatCommand extends Command {
    public RepeatCommand() {
        setCommand(prefix + "repeat");
        addPermission("everyone");
        setTopic("music");
        setDescription("enables queue repetition. Now playing song will repeat as the last song");
        setHelpString("<true|false>");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length >= 1) {
            String inputBoolean = argStrings[0];
            if (inputBoolean.equals("true") || inputBoolean.equals("false")) {
                boolean isRepeat = Boolean.parseBoolean(inputBoolean);
                main.Commands.sendBeautifulMessage(event, "Repetition of songs has been set to: "
                        + Commands.player.setRepeat(event.getTextChannel(), isRepeat));
            } else {
                main.Commands.sendMessage(event, getHelp());
            }
        } else {
            main.Commands.sendMessage(event, this.getHelpString());
        }
    }
}
