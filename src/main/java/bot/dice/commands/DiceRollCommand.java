package bot.dice.commands;

import java.util.Random;

import bot.main.MessageUtils;
import bot.shared.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DiceRollCommand extends Command {
    Random random = new Random();

    public DiceRollCommand() {
        addPermission("everyone");
        addCommendPrefix("roll");
        setTopic("util");
        setDescription("Roll a dice");
        setHelpString("<Maximum number to roll. E.g. for a D6 dice, input 6 or d6>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        try {
            int maximumNumber = Integer.parseInt(argStrings[0].replace("d", ""));
            MessageUtils.sendBeautifulMessage(event, String.format("%s", random.nextInt(maximumNumber) + 1));
        } catch (NumberFormatException e) {
            MessageUtils.sendMessage(event, getHelp());
        }
    }
}
