package bot.audioplayer.commands;

import bot.main.CommandManager;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RepeatCommand extends Command {
    public RepeatCommand() {
        addPermission("everyone");
        addCommendPrefix("repeat");
        setDescription("enables queue repetition. Now playing song will repeat as the last song");
        setTopic("music");
        setHelpString("<true|false>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String inputBoolean = argStrings[0];
        if (inputBoolean.equals("true") || inputBoolean.equals("false")) {
            boolean isRepeat = Boolean.parseBoolean(inputBoolean);
            MessageUtils.sendBeautifulMessage(event, "Repetition of songs has been set to: "
                    + CommandManager.player.setRepeat(event.getGuild(), isRepeat));
        } else {
            MessageUtils.sendMessage(event, getHelp());
        }
    }
}
