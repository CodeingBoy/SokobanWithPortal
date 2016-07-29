package sokoban.game.engine.scenes;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-29-0029.
 */
public abstract class SuperScene extends Scene {
    private int sleepNanoSecond = 10;
    private long curTime, lastTime;
    private double nsPerSec;

    public SuperScene() {

    }

    @Override
    public void onPrepareRendering() {
        curTime = System.nanoTime();
        lastTime = curTime;
        beforeRendering();
    }

    public abstract void beforeRendering();

    @Override
    public final void onRendering() {
        curTime = System.nanoTime();
        nsPerSec = curTime - lastTime;
        lastTime = curTime;

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
