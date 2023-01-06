package bot.audioplayer.commands;

import bot.main.CommandManager;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SeekCommand extends Command {
    public SeekCommand() {
        addPermission("everyone");
        addCommendPrefix("seek");
        setDescription("seeks forward in the current playing song");
        setTopic("music");
        setHelpString("<seconds to skip in the current song>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        int inputSeconds = Integer.parseInt(argStrings[0]);
        if (inputSeconds < 0)
            return;
        long position = CommandManager.player.seek(event.getGuild(), inputSeconds);
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
            MessageUtils.sendBeautifulMessage(event, "skipped to minute " + minutesString + ":" + secondsString);
        }
    }

}
