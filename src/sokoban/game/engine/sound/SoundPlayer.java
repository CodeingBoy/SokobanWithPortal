package sokoban.game.engine.sound;

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
     * @return 创建的Clip对象 可以使用该对象对声音进行操纵
     */
    public static Clip PlaySoundFile(File soundFile) {
        Clip clip = getClipFromFile(soundFile);
        clip.start();
        return clip;
    }

    /**
     * 循环播放声音文件
     *
     * @param soundFile 欲播放之声音文件
     * @param times     循环次数
     * @return 创建的Clip对象 可以使用该对象对声音进行操纵
     */
    public static Clip PlayLoopSoundFile(File soundFile, int times) {
        Clip clip = getClipFromFile(soundFile);
        clip.loop(times);
        clip.start();
        return clip;
    }

    /**
     * 创建一个Clip对象，并将传入文件与之关联
     *
     * @param soundFile 欲关联之声音文件
     * @return 创建的Clip对象 可以使用该对象对声音进行操纵
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
     * 创建一个AudioInputStream对象，并将传入文件与之关联
     * @param file 欲关联之声音文件
     * @return 创建的AudioInputStream对象
     * @throws IOException
     */
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
