package server;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

import audioplayer.GuildMusicManager;
import database.SongHistoryTable;
import main.CommandManager;
import main.Startup;
import net.dv8tion.jda.api.entities.Guild;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class ApiController {

    @GetMapping(value = "nowplaying")
    public ResponseEntity<List<MusicStatusEntry>> sayHello() {
        Map<Long, GuildMusicManager> musicManagers = CommandManager.player.getMusicManagers();
        List<MusicStatusEntry> musicStatusEntries = musicManagers.entrySet().stream().map(entry -> {
            AudioTrack playingTrack = entry.getValue().player.getPlayingTrack();
            if (playingTrack == null)
                return null;
            String songTitle = playingTrack.getInfo().title;
            long position = playingTrack.getPosition();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(position);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(position)
                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(position));
            String currentPosition = String.format("%02d:%02d", minutes, seconds);
            String guildName = Startup.jda.getGuildById(entry.getKey()).getName();
            return new MusicStatusEntry(guildName, songTitle, currentPosition);
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return new ResponseEntity<>(musicStatusEntries, HttpStatus.OK);
    }

    @GetMapping(value = "guilds")
    public ResponseEntity<List<String>> getGuilds() {
        List<String> guildNames = Startup.jda.getGuilds().stream().map(Guild::getName).collect(Collectors.toList());
        return new ResponseEntity<>(guildNames, HttpStatus.OK);
    }

    @GetMapping(value = "songstatistics")
    public ResponseEntity<List<SongStatisticsEntry>> getSongStatistics() {
        List<SongStatisticsEntry> songStatistics = SongHistoryTable.getSongStatistics().entrySet().stream()
                .map(entry -> new SongStatisticsEntry(entry.getKey(), entry.getValue()))
                .sorted((entry1, entry2) -> entry1.numberOfPlays > entry2.numberOfPlays ? 1 : 0)
                .collect(Collectors.toList());
        return new ResponseEntity<>(songStatistics, HttpStatus.OK);
    }

    @GetMapping(value = "logs")
    public ResponseEntity<String> getLogs() {
        File logFile = new File("log.txt");
        try {
            String logFileText = FileUtils.readFileToString(logFile, StandardCharsets.UTF_8);
            return new ResponseEntity<>(logFileText, HttpStatus.OK);
        } catch (IOException e) {
            Logger.error(e);
        }
        return new ResponseEntity<>("Error retrieving logfile", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}