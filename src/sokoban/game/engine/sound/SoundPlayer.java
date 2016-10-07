package sokoban.game.engine.sound;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 声音播放器
 */
public class SoundPlayer {
    /**
     * 播放声音文件
     *
     * @param soundFile 欲播放之声音文件
     * @return 创建的 Clip 对象 可以使用该对象对声音进行操纵
     */
    public static Clip playSoundFile(File soundFile) {
        Clip clip = getClipFromFile(soundFile);
        clip.start();
        return clip;
    }

    /**
     * 循环播放声音文件
     *
     * @param soundFile 欲播放之声音文件
     * @param times     循环次数
     * @return 创建的 Clip 对象 可以使用该对象对声音进行操纵
     */
    public static Clip playLoopSoundFile(File soundFile, int times) {
        Clip clip = getClipFromFile(soundFile);
        clip.loop(times);
        clip.start();
        return clip;
    }

    /**
     * 创建一个 Clip 对象，并将传入文件与之关联
     *
     * @param soundFile 欲关联之声音文件
     * @return 创建的 Clip 对象 可以使用该对象对声音进行操纵
     */
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

    /**
     * 创建一个 AudioInputStream 对象，并将传入文件与之关联
     *
     * @param file 欲关联之声音文件
     * @return 创建的 AudioInputStream 对象
     * @throws IOException
     */
    public static AudioInputStream readAudioInputStreamFromFile(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
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

    /**
     * 设置 Clip 音量
     *
     * @param clip   欲操纵之 Clip 对象
     * @param volume 0~100之间的数，数值越大，音量越大
     */
    public static void setClipVolume(Clip clip, float volume) {
        if (clip == null)
            throw new IllegalArgumentException();

        if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN))
            throw new UnsupportedMediaException();

        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float max = gainControl.getMaximum();
        float min = gainControl.getMinimum();

        gainControl.setValue(min + (max - min) * (volume / 100));
    }
}
