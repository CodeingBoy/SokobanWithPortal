package sokoban.game.engine.sound;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by CodeingBoy on 2016-10-7-0007.
 */
public class SoundPlayer {
    public static Clip PlaySoundFile(File soundFile) {
        Clip clip = getClipFromFile(soundFile);
        clip.start();
        return clip;
    }

    public static Clip PlayLoopSoundFile(File soundFile,int times) {
        Clip clip = getClipFromFile(soundFile);
        clip.loop(times);
        clip.start();
        return clip;
    }

    public static Clip getClipFromFile(File soundFile) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = readAudioInputStreamFromFile(soundFile);
            try {
                clip.open(audioInputStream);
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            return clip;
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AudioInputStream readAudioInputStreamFromFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }

}
