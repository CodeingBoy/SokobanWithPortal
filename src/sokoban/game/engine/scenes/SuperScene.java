package sokoban.game.engine.scenes;

import sokoban.game.engine.graphics.WindowRatioKeeper;
import sokoban.game.engine.input.handler.KeyboardInputHandler;
import sokoban.game.engine.input.handler.MouseInputHandler;
import sokoban.game.utils.FrameRateDrawable;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by CodeingBoy on 2016-7-29-0029.
 */
public abstract class SuperScene extends Scene {
    protected KeyboardInputHandler keyboardInputHandler;
    protected MouseInputHandler mouseInputHandler;
    protected FrameRateDrawable frameRateDrawable;
    protected WindowRatioKeeper windowRatioKeeper = null;
    private int sleepNanoSecond = 10;
    private long curTime, lastTime;
    private double nsPerSec;
    private boolean initialized = false;

    public SuperScene() {

    }

    public SuperScene(KeyboardInputHandler keyboardInputHandler, MouseInputHandler mouseInputHandler, FrameRateDrawable frameRateDrawable) {
        this.keyboardInputHandler = keyboardInputHandler;
        this.mouseInputHandler = mouseInputHandler;
        this.frameRateDrawable = frameRateDrawable;
    }

    public SuperScene(KeyboardInputHandler keyboardInputHandler, MouseInputHandler mouseInputHandler) {
        this.keyboardInputHandler = keyboardInputHandler;
        this.mouseInputHandler = mouseInputHandler;
    }

    public void setFrameRateDrawable(FrameRateDrawable frameRateDrawable) {
        this.frameRateDrawable = frameRateDrawable;
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

        if (frameRateDrawable != null)
            frameRateDrawable.initialize();

        beforeRendering();
    }

    @Override
    public void onInitialize() {
        canvas = new Canvas();
        canvas.setBackground(Color.black);

        if (windowRatioKeeper == null) {
            window.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    canvas.setBounds(0, 0, e.getComponent().getWidth(), e.getComponent().getHeight());
                }
            });
        } else {
            windowRatioKeeper.setCanvas(canvas);
            window.addComponentListener(windowRatioKeeper);
        }

        if (keyboardInputHandler != null) {
            canvas.addKeyListener(keyboardInputHandler.getInput());
            canvas.addMouseListener(mouseInputHandler.getInput());
        }

        if (mouseInputHandler != null) {
            canvas.addMouseMotionListener(mouseInputHandler.getInput());
            canvas.addMouseWheelListener(mouseInputHandler.getInput());
        }

        initialized = true;
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
                renderFrameRate(g, delta);
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

    /**
     * 渲染函数 渲染线程会调用此函数 请重写该函数进行渲染
     *
     * @param g     图形对象
     * @param delta 时间增量
     */
    public abstract void render(Graphics g, double delta);

    public void renderFrameRate(Graphics g, double delta) {
        if (frameRateDrawable != null)
            frameRateDrawable.draw(g, delta);
    }
}
