package audioplayer.commands.play;

import java.util.List;

import main.CommandManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import shared.commands.SlashCommand;

public class PlayCommand extends SlashCommand {

    public PlayCommand() {
        addSubCommand(new PlayRecommendedCommand());
        addSubCommand(new PlayRandomCommand());
        addPermission("everyone");
        addCommendPrefix("pl", "play");
        setTopic("music");
        setDescription("Play music by adding a search-term or by adding a link");
        setHelpString("<youtube search term> | <youtube url>");
        setMinArguments(1);
    }

    @Override
    protected void handleImpl(MessageReceivedEvent event, String[] argStrings) {
        String trackUrl = "";
        if (!(argStrings[0].contains("http") || argStrings[0].contains("https"))) {
            trackUrl = "ytsearch: ";
            for (int i = 0; i < argStrings.length; i++) {
                trackUrl += argStrings[i] + " ";
            }
        } else {
            trackUrl = argStrings[0];
        }

        CommandManager.player.loadAndPlay(event, trackUrl);
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        OptionMapping url = event.getOption("url");
        OptionMapping search = event.getOption("search");
        if (url != null) {
            CommandManager.player.loadAndPlay(event, url.getAsString());
        } else if (search != null) {
            CommandManager.player.loadAndPlay(event, "ytsearch: " + search.getAsString());
        } else {
            event.getHook().sendMessage("No url or search term given").queue();
        }
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "url", "Play an url"),
                new OptionData(OptionType.STRING, "search", "Play from any search term"));
    }

    @Override
    public List<String> getNames() {
        return List.of("play");
    }

    @Override
    public List<SlashCommand> getSlashSubCommands() {
        return List.of(new PlayRecommendedCommand());
    }

}
