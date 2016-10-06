package sokoban.game.engine.sound;

import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-10-7-0007.
 */
public class SoundManager {
    private Map<String, Clip> clipList = new HashMap<>();

    private static SoundManager ourInstance = new SoundManager();

    public static SoundManager getInstance() {
        return ourInstance;
    }

    private SoundManager() {
    }

    public void addClip(String name, Clip clip) {
        if (clipList.containsKey(name))
            return;
        clipList.put(name, clip);
    }

    public void addClip(String name, File soundFile) {
        if (clipList.containsKey(name))
            return;
        clipList.put(name, SoundPlayer.getClipFromFile(soundFile));
    }

    public Clip getClip(String name) {
        return clipList.get(name);
    }

    public void startClip(String name) {
        Clip clip = getClip(name);
        if (clip == null)
            throw new IllegalArgumentException();

        clip.start();
    }

    public void loopClip(String name) {
        Clip clip = getClip(name);
        if (clip == null)
            throw new IllegalArgumentException();

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setLoopPoint(String name, int start, int end) {
        Clip clip = getClip(name);
        if (clip == null)
            throw new IllegalArgumentException();

        clip.setLoopPoints(start, end);
    }

    public void stopClip(String name) {
        Clip clip = getClip(name);
        if (clip == null)
            throw new IllegalArgumentException();

        clip.stop();
    }

    public void resetClip(String name) {
        Clip clip = getClip(name);
        if (clip == null)
            throw new IllegalArgumentException();

        clip.setFramePosition(0);
    }
}
