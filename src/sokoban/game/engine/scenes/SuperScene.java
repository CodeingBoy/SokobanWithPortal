package sokoban.game.engine.scenes;

import sokoban.game.engine.input.handler.KeyboardInputHandler;
import sokoban.game.engine.input.handler.MouseInputHandler;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-29-0029.
 */
public abstract class SuperScene extends Scene {
    private int sleepNanoSecond = 10;
    private long curTime, lastTime;
    private double nsPerSec;
    protected KeyboardInputHandler keyboardInputHandler;
    protected MouseInputHandler mouseInputHandler;

    public SuperScene() {

    }

    public SuperScene(KeyboardInputHandler keyboardInputHandler, MouseInputHandler mouseInputHandler) {
        this.keyboardInputHandler = keyboardInputHandler;
        this.mouseInputHandler = mouseInputHandler;
    }

    public void setKeyboardInputHandler(KeyboardInputHandler keyboardInputHandler) {
        this.keyboardInputHandler = keyboardInputHandler;
    }

    public void setMouseInputHandler(MouseInputHandler mouseInputHandler) {
        this.mouseInputHandler = mouseInputHandler;
    }

    @Override
    public void onPrepareRendering() {
        curTime = System.nanoTime();
        lastTime = curTime;
        beforeRendering();
    }

    @Override
    public void onInitialize() {
        if (keyboardInputHandler != null) {
            canvas.addKeyListener(keyboardInputHandler.getInput());
            canvas.addMouseListener(mouseInputHandler.getInput());
        }

        if (mouseInputHandler != null) {
            canvas.addMouseMotionListener(mouseInputHandler.getInput());
            canvas.addMouseWheelListener(mouseInputHandler.getInput());
        }
    }

    public abstract void beforeRendering();

    @Override
    public final void onRendering() {
        curTime = System.nanoTime();
        nsPerSec = curTime - lastTime;
        lastTime = curTime;

        if (keyboardInputHandler != null) {
            keyboardInputHandler.poll();
            keyboardInputHandler.processInput();
        }
        if (mouseInputHandler != null) {
            mouseInputHandler.poll();
            mouseInputHandler.processInput();
        }

        afterTiming();

        renderLoop(nsPerSec / 1.0E9);
        try {
            Thread.sleep(sleepNanoSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void afterTiming();

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

    public int getSleepNanoSecond() {
        return sleepNanoSecond;
    }

    public void setSleepNanoSecond(int sleepNanoSecond) {
        this.sleepNanoSecond = sleepNanoSecond;
    }

    public abstract void render(Graphics g, double delta);
}
