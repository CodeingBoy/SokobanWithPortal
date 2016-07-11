package sokoban.game.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public class KeyboardInput implements KeyListener {
    private int[] keyLastFrames = new int[256];
    private boolean[] isKeyPressing = new boolean[256];

    public boolean isKeyDown(int keyCode) {
        return keyLastFrames[keyCode] > 0;
    }

    public boolean isKeyDownOnce(int keyCode) {
        return keyLastFrames[keyCode] == 1;
    }

    public synchronized void poll() {
        for (int i = 0; i < isKeyPressing.length; i++) {
            if (isKeyPressing[i])
                keyLastFrames[i]++;
            else
                keyLastFrames[i] = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < isKeyPressing.length)
            isKeyPressing[keyCode] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < isKeyPressing.length)
            isKeyPressing[keyCode] = false;
    }
}
