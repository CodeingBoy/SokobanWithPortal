package sokoban.game.engine;

import sokoban.game.engine.input.handler.KeyboardInputHandler;
import sokoban.game.engine.input.handler.MouseInputHandler;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public abstract class SuperScene extends Scene {
    private int sleepNanoSecond = 10;
    private long curTime, lastTime;
    private double nsPerSec;
    private KeyboardInputHandler keyboardInputHandler;
    private MouseInputHandler mouseInputHandler;

    public SuperScene(KeyboardInputHandler keyboardInputHandler, MouseInputHandler mouseInputHandler) {
        this.keyboardInputHandler = keyboardInputHandler;
        this.mouseInputHandler = mouseInputHandler;
    }

    public int getSleepNanoSecond() {
        return sleepNanoSecond;
    }

    public void setSleepNanoSecond(int sleepNanoSecond) {
        this.sleepNanoSecond = sleepNanoSecond;
    }

    @Override
    public void onInitialize() {
        canvas.addKeyListener(keyboardInputHandler.getInput());
        canvas.addMouseListener(mouseInputHandler.getInput());
        canvas.addMouseMotionListener(mouseInputHandler.getInput());
        canvas.addMouseWheelListener(mouseInputHandler.getInput());
    }

    @Override
    public void onPrepareRendering() {
    }

    @Override
    public void onRendering() {
        curTime = System.nanoTime();
        nsPerSec = curTime - lastTime;
        lastTime = curTime;

        keyboardInputHandler.poll();
        keyboardInputHandler.processInput();
        mouseInputHandler.poll();
        mouseInputHandler.processInput();
        renderLoop(nsPerSec / 1.0E9);
        try {
            Thread.sleep(sleepNanoSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final void renderLoop(double delta) {
        do {
            do {
                Graphics g = bufferStrategy.getDrawGraphics();
                g.clearRect(0, 0, getWidth(), getHeight());
                render(g, delta);

                if (g != null) {
                    g.dispose();
                }
            } while (bufferStrategy.contentsRestored());
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }

    protected abstract void render(Graphics g, double delta);
}
