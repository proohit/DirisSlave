package bot.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.tinylog.Logger;

import bot.Startup;
import bot.database.Configuration;
import bot.database.ConfigurationTable;
import net.dv8tion.jda.api.entities.Guild;

public class ConfigurationManager {
    private static final String DEFAULT_PREFIX = ".";
    private static final String DEFAULT_MUSICCHANNEL = "everywhere";
    Map<Long, Configuration> serverConfigurations = new HashMap<>();

    ConfigurationManager() {
        List<Configuration> configurations = ConfigurationTable.getConfigurations();
        configurations.forEach(configuration -> serverConfigurations.put(configuration.getGuildId(), configuration));

        List<Guild> newGuilds = Startup.jda.getGuilds().stream()
                .filter(newGuild -> !serverConfigurations.containsKey(newGuild.getIdLong()))
                .collect(Collectors.toList());

        Logger.info("Retrieved new Guilds: {}", newGuilds);

        newGuilds.forEach(newGuild -> {
            var createdConfiguration = ConfigurationTable
                    .createConfiguration(new Configuration(newGuild.getIdLong(), DEFAULT_PREFIX, DEFAULT_MUSICCHANNEL));
            serverConfigurations.put(newGuild.getIdLong(), createdConfiguration);
        });

        Logger.info("Bot logged in at Guilds: {}", serverConfigurations.values());

    }

    public Configuration getConfigurationForGuild(long guildId) {
        return serverConfigurations.get(guildId);
    }

    public String getMusicchannelForGuild(long guildId) {
        Configuration serverConfiguration = getOrCreateIfConfigurationNotExists(guildId);
        return serverConfiguration.getMusicChannel();
    }

    public String getPrefixForGuild(long guildId) {
        Configuration serverConfiguration = getOrCreateIfConfigurationNotExists(guildId);
        return serverConfiguration.getPrefix();
    }

    private Configuration getOrCreateIfConfigurationNotExists(long guildId) {
        Configuration serverConfiguration = serverConfigurations.get(guildId);
        if (serverConfiguration == null) {
            serverConfiguration = ConfigurationTable.getConfigurationByGuildId(guildId);
            if (serverConfiguration == null) {
                serverConfiguration = ConfigurationTable
                        .createConfiguration(new Configuration(guildId, DEFAULT_PREFIX, DEFAULT_MUSICCHANNEL));
            }
        }
        return serverConfiguration;
    }

    // private Configuration updateOrCreateIfConfigurationNotExists(Configuration
    // configuration) {
    // Configuration serverConfiguration =
    // serverConfigurations.get(configuration.getGuildId());

    // if (serverConfiguration == null) {
    // serverConfiguration =
    // ConfigurationTable.getConfigurationByGuildId(configuration.getGuildId());
    // if (serverConfiguration == null) {
    // serverConfiguration = ConfigurationTable.createConfiguration(configuration);
    // } else {
    // serverConfiguration = ConfigurationTable.updateConfiguration(configuration);
    // }
    // serverConfigurations.put(serverConfiguration.getGuildId(),
    // serverConfiguration);
    // }
    // return serverConfiguration;
    // }

    public void setMusicchannelForGuild(long guildId, String channelName) {
        Configuration updatedConfiguration = serverConfigurations.get(guildId);

        if (updatedConfiguration == null) {
            updatedConfiguration = new Configuration(guildId, DEFAULT_PREFIX, channelName);
            ConfigurationTable.createConfiguration(updatedConfiguration);
        } else {
            updatedConfiguration.setMusicChannel(channelName);
            ConfigurationTable.updateConfiguration(updatedConfiguration);
        }
    }

    public void setPrefixForGuild(long guildId, String prefix) {
        Configuration updatedConfiguration = serverConfigurations.get(guildId);

        if (updatedConfiguration == null) {
            updatedConfiguration = new Configuration(guildId, prefix, DEFAULT_MUSICCHANNEL);
            ConfigurationTable.createConfiguration(updatedConfiguration);
        } else {
            updatedConfiguration.setPrefix(prefix);
            ConfigurationTable.updateConfiguration(updatedConfiguration);
        }
    }

    public boolean isMusicchannelOfGuild(long guildId, String channelName) {
        String registeredMusicChannel = serverConfigurations.get(guildId).getMusicChannel();
        if (DEFAULT_MUSICCHANNEL.equals(registeredMusicChannel) || channelName.equals(registeredMusicChannel)) {
            return true;
        } else {
            return false;
        }
    }

}
