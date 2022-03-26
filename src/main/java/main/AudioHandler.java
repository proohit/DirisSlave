package main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.google.gson.Gson;

import org.tinylog.Logger;
import org.vosk.Model;
import org.vosk.Recognizer;

import audioplayer.commands.StopCommand;
import audioplayer.commands.play.PlayCommand;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.UserAudio;
import net.dv8tion.jda.api.entities.Guild;

public class AudioHandler implements AudioReceiveHandler {
    Recognizer rec;
    Date lastDate;

    public AudioHandler() {
        var vc = Startup.jda.getVoiceChannelById(819357140241743872l);
        Startup.jda.getGuildById(385547820087640064l).getAudioManager().openAudioConnection(vc);
        Startup.jda.getGuildById(385547820087640064l).getAudioManager().setReceivingHandler(this);
        String modelPath = AudioHandler.class.getClassLoader().getResource("model").getPath();
        Logger.info("Loading model from " + modelPath);
        try {
            Model model = new Model(modelPath);
            rec = new Recognizer(model, 8000);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public boolean canReceiveUser() {
        return true;
    }

    private class Transcript {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Override
    public void handleUserAudio(UserAudio userAudio) {
        Guild guild = Startup.jda.getGuildById(385547820087640064l);
        if (userAudio.getUser().isBot()) {
            return;
        }
        lastDate = new Date();
        if (rec != null) {
            try {
                byte[] voice = userAudio.getAudioData(1.0);
                byte[] transcodedVoice = transcodeVoice(voice);
                if (rec.acceptWaveForm(transcodedVoice, transcodedVoice.length)) {
                    String finalResult = rec.getFinalResult();
                    Logger.info("Result {}", finalResult);
                    Gson gson = new Gson();
                    Transcript transcript = gson.fromJson(finalResult, Transcript.class);
                    String[] rawArgs = transcript.getText().split(" ");
                    int idx = List.of(rawArgs).indexOf("play");
                    if (idx != -1) {
                        String[] args = Arrays.copyOfRange(rawArgs, idx, rawArgs.length);
                        PlayCommand cmd = new PlayCommand();
                        cmd.handle(guild, args);
                    }
                    idx = List.of(rawArgs).indexOf("stop");
                    if (idx != -1) {
                        StopCommand cmd = new StopCommand();
                        cmd.handle(guild);
                    }
                } else {
                    rec.getPartialResult();
                }
            } catch (Exception e) {
                Logger.error(e);
            }
        }
    }

    private byte[] transcodeVoice(byte[] voice) throws IOException {
        AudioFormat originalFormat = new AudioFormat(48000.0f, 16, 2, true, true);
        AudioFormat targetFormat = new AudioFormat(8000.0f, 16, 1, true, false);

        int length = voice.length;
        ByteArrayInputStream bais = new ByteArrayInputStream(voice);
        AudioInputStream originalStream = new AudioInputStream(bais, originalFormat, length);
        AudioInputStream transcodedStream = AudioSystem.getAudioInputStream(targetFormat, originalStream);
        return transcodedStream.readAllBytes();
    }

}
