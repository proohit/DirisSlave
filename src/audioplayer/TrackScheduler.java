package audioplayer;


import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        AudioTrack nextTrack = queue.poll();
        if (nextTrack == null) {
            player.stopTrack();
            audioplayer.AudioPlayer.getLastManager().closeAudioConnection();
        }
        player.startTrack(nextTrack, false);
    }

    public Stream<AudioTrack> getQueue() {
        return queue.stream();
    }

    public void stop() {
        while (!queue.isEmpty()) queue.poll();
        player.destroy();
        AudioManager manager = audioplayer.AudioPlayer.getLastManager();
        if(manager.isConnected() || manager.isAttemptingToConnect()) manager.closeAudioConnection();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    public void skipTo(int pos) {
        if (pos > queue.size() || pos <= 0) return;
        int i = 0;
        Iterator<AudioTrack> it = queue.iterator();

        while (it.hasNext()) {
            if (++i == pos) {
                nextTrack();
                return;
            } else {
                    queue.poll();
                    it.next();

            }
        }
    }

    public void remove(int pos) {
        if (pos > queue.size() || pos <= 0) return;
        int i = 1;
        Iterator<AudioTrack> it = queue.iterator();
        while (it.hasNext()) {
            if (i++ == pos) {
                queue.remove(it.next());
                return;
            } else {
                it.next();
            }
        }
    }
}