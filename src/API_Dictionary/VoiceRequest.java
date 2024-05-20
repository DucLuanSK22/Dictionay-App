package API_Dictionary;

import com.voicerss.tts.*;
import javafx.concurrent.Task;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileOutputStream;


import static Constants.codekey.APIKEY;
import static com.voicerss.tts.Languages.*;



public class VoiceRequest extends Task<Clip> {
    private String text;
    public static double speed = 1;

    private String language;

    public VoiceRequest(String text, String language) {
        this.text = text;
        this.language = language;
    }

    @Override
    protected Clip call() throws Exception {
        String PATH = "src/resources/Audio/audio.wav";
        VoiceProvider tts = new VoiceProvider(APIKEY);
        VoiceParameters params = new VoiceParameters(text, AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setLanguage(language);
        System.out.println(language);
        params.setSSML(false);
        params.setRate((int) Math.round(-2.9936 * speed * speed + 15.2942 * speed - 12.7612));

        byte[] voice;
        voice = tts.speech(params);
        FileOutputStream fos = new FileOutputStream(PATH);
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
        File audioFile = new File(PATH);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }
}