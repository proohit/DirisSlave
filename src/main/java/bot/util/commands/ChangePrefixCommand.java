package bot.util.commands;

import bot.main.CommandManager;
import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ChangePrefixCommand extends Command {
    public ChangePrefixCommand() {
        addPermission("Bananenchefs");
        addCommendPrefix("prefix");
        setTopic("util");
        setDescription("Changes the command prefix");
        setHelpString("<string of the new prefix>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        long guildId = event.getGuild().getIdLong();
        CommandManager.CONFIGURATION_MANAGER.setPrefixForGuild(guildId, argStrings[0]);
        MessageUtils.sendBeautifulMessage(event,
                String.format("Changed prefix to %s", CommandManager.CONFIGURATION_MANAGER.getPrefixForGuild(guildId)));
    }
}
