package shared.commands;

import java.util.List;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class SlashCommand extends Command {

    public abstract void handle(SlashCommandInteractionEvent event);

    public abstract List<OptionData> getOptions();

    public abstract List<String> getNames();

    public abstract List<SlashCommand> getSlashSubCommands();

}
