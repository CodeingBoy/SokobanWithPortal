package sokoban.game.engine.sound;

import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 声音管理器
 * 用于全局性的声音播放及管理
 */
public class SoundManager {
    private Map<String, Clip> clipList = new HashMap<>();

    private static SoundManager ourInstance = new SoundManager();

    /**
     * 获得唯一的 SoundManager 实例
     * @return SoundManager实例
     */
    public static SoundManager getInstance() {
        return ourInstance;
    }

    private SoundManager() {
    }

    /**
     * 向管理器添加 Clip 对象
     * @param name 标识名
     * @param clip Clip 对象
     */
    public void addClip(String name, Clip clip) {
        if (clipList.containsKey(name))
            return;
        clipList.put(name, clip);
    }

    /**
     * 使用传入的声音文件构造 Clip 对象，并将其添加到管理器中
     * @param name 标识名
     * @param soundFile 欲添加的声音文件
     */
    public void addClip(String name, File soundFile) {
        if (clipList.containsKey(name))
            return;
        clipList.put(name, SoundPlayer.getClipFromFile(soundFile));
    }

    /**
     * 获得传入标识名的 Clip 引用
     * @param name 标识名
     * @return Clip 对象
     */
    public Clip getClip(String name) {
        return clipList.get(name);
    }

    /**
     * 开始播放指定的 Clip
     * @param name 标识名
     */
    public void startClip(String name) {
        Clip clip = getClip(name);
        if (clip == null)
            throw new IllegalArgumentException();

        clip.start();
    }

    /**
     * 循环（不间断地）播放指定的 Clip
     * @param name 标识名
     */
    public void loopClip(String name) {
        Clip clip = getClip(name);
        if (clip == null)
            throw new IllegalArgumentException();

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * 设置循环区间
     * @param name 标识名
     * @param start 循环区间开始
     * @param end 循环区间结束
     * @see Clip#setLoopPoints(int, int)
     */
    public void setLoopPoint(String name, int start, int end) {
        Clip clip = getClip(name);
        if (clip == null)
            throw new IllegalArgumentException();

        clip.setLoopPoints(start, end);
    }

    /**
     * 停止播放指定的 Clip
     * @param name 标识名
     */
    public void stopClip(String name) {
        Clip clip = getClip(name);
        if (clip == null)
            throw new IllegalArgumentException();

        clip.stop();
    }

    /**
     * 复位指定的 Clip
     * @param name 标识名
     */
    public void resetClip(String name) {
        Clip clip = getClip(name);
        if (clip == null)
            throw new IllegalArgumentException();

        clip.setFramePosition(0);
    }
}
