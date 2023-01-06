package bot.server;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tinylog.Logger;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import bot.Startup;
import bot.audioplayer.GuildMusicManager;
import bot.database.SongHistoryTable;
import bot.main.CommandManager;
import bot.server.LogEntry.LogLevel;
import net.dv8tion.jda.api.entities.Guild;

@Controller
@RequestMapping(value = "/api", produces = "application/json")
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
    public ResponseEntity<List<LogEntry>> getLogs() {
        File logFile = new File("log.txt");

        try {
            String logFileText = FileUtils.readFileToString(logFile, StandardCharsets.UTF_8);
            Matcher entryMatcher = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}").matcher(logFileText);
            List<Integer> startIndexes = new ArrayList<>();
            List<Integer> endIndexes = new ArrayList<>();
            List<LogEntry> logEntries2 = new ArrayList<>();
            while (entryMatcher.find()) {
                startIndexes.add(entryMatcher.start());
                endIndexes.add(entryMatcher.end());
            }
            for (int index = 0; index < startIndexes.size(); index++) {
                String content;
                if (index + 1 >= startIndexes.size()) {
                    content = logFileText.substring(endIndexes.get(index));
                } else {
                    content = logFileText.substring(endIndexes.get(index), startIndexes.get(index + 1));
                }
                int indexOfStackTrace = -1;
                Matcher matcher = Pattern.compile("\\s+(at)\\s+\\w+\\.+").matcher(content);
                if (matcher.find()) {
                    indexOfStackTrace = matcher.start();
                }
                String stackTrace;
                if (indexOfStackTrace < 0) {
                    stackTrace = "";
                } else {
                    stackTrace = content.substring(indexOfStackTrace);
                    content = content.substring(0, indexOfStackTrace);
                }
                LogLevel level = null;
                for (LogLevel it : LogLevel.values()) {
                    if (content.contains(it.getLevel())) {
                        level = it;
                        content = content.substring(content.indexOf(it.getLevel()) + it.getLevel().length());
                    }
                }
                if (level == null)
                    level = LogLevel.UNKNOWN;

                var entry = new LogEntry(content, logFileText.substring(startIndexes.get(index), endIndexes.get(index)),
                        level, stackTrace);
                logEntries2.add(entry);
            }
            Collections.reverse(logEntries2);
            return new ResponseEntity<>(logEntries2, HttpStatus.OK);
        } catch (IOException e) {
            Logger.error(e);
        }
        return null;
    }

}