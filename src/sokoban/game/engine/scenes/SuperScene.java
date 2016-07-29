package sokoban.game.engine.scenes;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class SuperScene extends Scene {
    private int sleepNanoSecond = 10;
    private long curTime, lastTime;
    private double nsPerSec;

    public SuperScene() {

    }

    public int getSleepNanoSecond() {
        return sleepNanoSecond;
    }

    public void setSleepNanoSecond(int sleepNanoSecond) {
        this.sleepNanoSecond = sleepNanoSecond;
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onInitialize() {
        // canvas.addKeyListener(keyboardInputHandler.getInput());
        // canvas.addMouseListener(mouseInputHandler.getInput());
        // canvas.addMouseMotionListener(mouseInputHandler.getInput());
        // canvas.addMouseWheelListener(mouseInputHandler.getInput());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onPrepareRendering() {
        curTime = System.nanoTime();
        lastTime = curTime;
    }

    @Override
    public void onRendering() {
        curTime = System.nanoTime();
        nsPerSec = curTime - lastTime;
        lastTime = curTime;

        renderLoop(nsPerSec / 1.0E9);
        try {
            Thread.sleep(sleepNanoSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onExitingRendering() {

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

    public void render(Graphics g, double delta){

    }
}
