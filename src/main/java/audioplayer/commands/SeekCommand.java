package audioplayer.commands;

import main.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shared.commands.Command;

public class SeekCommand extends Command {
    public SeekCommand() {
        addPermission("everyone");
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        if (argStrings.length == 1) {
            int inputSeconds = Integer.parseInt(argStrings[0]);
            if (inputSeconds < 0)
                return;
            long position = Commands.player.seek(event.getTextChannel(), inputSeconds);
            if (position > 0) {
                int seconds = (int) position / 1000;
                int minutes = seconds / 60;
                seconds = seconds % 60;
                String secondsString = String.valueOf(seconds);
                String minutesString = String.valueOf(minutes);
                if (secondsString.length() < 2)
                    secondsString = "0" + secondsString;
                if (minutesString.length() < 2)
                    minutesString = "0" + minutesString;
                Commands.sendBeautifulMessage(event, "skipped to minute " + minutesString + ":" + secondsString);
            }
        }
    }

    @Override
    protected String[] defineCommand() {
        return new String[] { "seek" };
    }

    @Override
    protected String defineDescription() {
        return "seeks forward in the current playing song";
    }

    @Override
    protected String defineTopic() {
        return "music";
    }

    @Override
    protected String defineHelpString() {
        return "<seconds to skip in the current song>";
    }
}
