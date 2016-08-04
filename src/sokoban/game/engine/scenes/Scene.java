package sokoban.game.engine.scenes;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */

import sokoban.game.engine.GameWindow;
import sokoban.utils.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public abstract class Scene implements Runnable {
    protected Canvas canvas;
    protected BufferStrategy bufferStrategy;
    private Thread renderThread;
    private boolean rendering = false;
    protected GameWindow window;

    /**
     * GameWindow 初始化或切换场景时会调用此函数 可以重写该函数以更改游戏窗口的部分属性
     *
     * @see GameWindow#GameWindow(Dimension, String, Scene)
     */
    public abstract void onPrepare();

    /**
     * 即将开始渲染之前会调用此函数 请重写该函数以确保您的场景准备好进行渲染<br/>
     * 该函数将在游戏窗口开始渲染前被调用
     *
     * @see #onInitialize()
     */
    public abstract void onInitialize();

    /**
     * 场景被切换或游戏窗口关闭时会调用此函数 请重写该函数以确保您的资源被正确释放
     *
     * @see GameWindow#requestSwitchScene(Scene)
     * @see GameWindow#GameWindow(Dimension, String, Scene)
     */
    public abstract void onDestroy();

    /**
     * 进入游戏渲染循环之前会调用此函数 您可以重写该函数进行初始化
     */
    public abstract void onPrepareRendering();

    /**
     * 游戏渲染循环中会调用此函数 您可以重写此函数对画面进行渲染
     */
    public abstract void onRendering();

    /**
     * 游戏渲染线程退出之前会调用此函数 您可以重写此函数对资源执行清理
     */
    public abstract void onExitingRendering();

    /**
     * 初始化场景 为即将渲染做准备<br/>
     * 将由 GameWindow 类中的 showWindow 方法进行调用 请勿直接调用此函数<br/>
     * 欲自定义即将显示中的过程 请重写 onInitialize() 函数
     *
     * @throws IllegalArgumentException 所需的参数不足 请补全参数
     * @see #onInitialize
     */
    public final void Initialize() throws IllegalArgumentException {
        onInitialize();

        // add(canvas); // will be added by showWindow() in class GameWindow
    }

    public void setWindow(GameWindow window) {
        this.window = window;
    }

    public final void startRendering() {
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        renderThread = new Thread(this);
        renderThread.start();
    }

    public final void destroy() {
        onDestroy();
        Log.d("stopping rendering...");
        rendering = false;
        try {
            renderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("render thread stopped!");

        Log.d("Exiting...");
    }

    /**
     * 返回画布 canvas<br/>
     * return var canvas
     *
     * @return 返回画布 canvas 的引用<br/>
     * return reference to canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * 游戏渲染线程函数<br/>
     * 您可以通过重写 onPrepareRendering(), onRendering(), onExitingRendering() 来更改场景在渲染线程中的行为
     *
     * @see #onPrepareRendering()
     * @see #onRendering()
     * @see #onExitingRendering()
     */
    @Override
    public final void run() {
        rendering = true;
        onPrepareRendering();

        while (rendering) {
            onRendering();
        }

        onExitingRendering();
    }

    public final int getWidth() {
        return canvas.getWidth();
    }

    public final int getHeight() {
        return canvas.getHeight();
    }

    public final void requestSwitchScene(Scene newScene) {
        // 给消息队列投递消息 让 Swing 消息队列处理该事件
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.switchScene(newScene);
            }
        });
    }
}
