package sokoban.game.engine;

import sokoban.dialog.SettingDialog;
import sokoban.game.engine.scenes.Scene;
import sokoban.utils.Log;
import sokoban.utils.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 承载游戏内容的框架窗口
 */
public class GameWindow extends JFrame {
    private final static Class LOGCLASS = GameWindow.class;
    private DisplayMode lastDisplayMode = null;
    private Scene scene;

    /**
     * 构造游戏窗口
     *
     * @param size  窗口初始大小
     * @param title 窗口标题
     * @param scene 窗口起始场景
     * @throws HeadlessException
     */
    public GameWindow(Dimension size, String title, Scene scene) throws HeadlessException {
        setLayout(null);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(size);
        setTitle(title);
        setIgnoreRepaint(true); // active rendering, need not passive repaint 主动渲染，无须被动渲染
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Log.d(LOGCLASS, "closing window");
                super.windowClosing(e);
                close();
            }
        });
        Log.d(LOGCLASS, "properties set!");

        if (Settings.isFullScreen()) { // entering fullscreen mode
            onEnteringFullScreen();
        }

        if (scene != null) {
            Log.d(LOGCLASS, "preparing scene...");
            this.scene = scene;
            scene.setWindow(this);
            scene.onPrepare();
        }

        SettingDialog.addSettingsChangedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                if (Settings.isFullScreen() && lastDisplayMode == null) { // entering fullscreen mode
                    onEnteringFullScreen();
                }

                if (!Settings.isFullScreen() && lastDisplayMode != null) { // exiting fullscreen mode
                    onExitingFullScreen();
                }

                if (Settings.isFullScreen() && Settings.getDisplayMode().toAWTDisplayMode() != graphicsDevice.getDisplayMode()) {
                    onExitingFullScreen();
                    onEnteringFullScreen();
                }
            }
        });
    }
    // public static void main(String[] args) {
    //     TestScene dialog = new TestScene();
    //     FrameRateCalculator frameRate = new FrameRateCalculator();
    //     frameRate.setShouldLog(true);
    //     dialog.setFrameRate(frameRate);
    //
    //     LogDialog logDialog = LogDialog.getInstance();
    //     logDialog.setVisible(true);
    //
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             dialog.createAndShowGUI();
    //             dialog.setVisible(true);
    //         }
    //     });
    // }

    public final JFrame getWindow() {
        return this;
    }

    /**
     * 显示窗口<br/>
     * 调用之前，请确保已正确设置场景及场景中的画布
     */
    public final void showWindow() {
        if (scene == null)
            throw new IllegalStateException("have no scene");

        Log.d(LOGCLASS, "initialing scene");
        scene.initialize();

        if (scene.getCanvas() == null) {
            throw new IllegalArgumentException("have not canvas");
        }
        getContentPane().add(scene.getCanvas());
        scene.getCanvas().setBounds(0, 0, getWidth(), getHeight());

        setVisible(true);

        scene.startRendering();
    }

    /**
     * 切换场景
     *
     * @param newScene 欲切换之场景
     */
    public final void switchScene(Scene newScene) {
        Log.d(GameWindow.class, "switching scene");
        Log.d(GameWindow.class, "old scene:" + scene.getClass().getName());
        Log.d(GameWindow.class, "new scene" + newScene.getClass().getName());
        if (isVisible()) {
            scene.destroy();
            scene.setWindow(null);
            remove(scene.getCanvas()); // 移除旧场景的画布 否则无法显示
            Log.d(LOGCLASS, "old scene destroyed");
            newScene.setWindow(this);
            Log.d(LOGCLASS, "preparing new scene");
            newScene.onPrepare();
            scene = newScene;
            showWindow();
        }
    }

    /**
     * 关闭窗口，会对场景进行destroy
     *
     * @see Scene#destroy()
     */
    public final void close() {
        if (lastDisplayMode != null) { // exiting fullscreen mode
            GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            graphicsDevice.setDisplayMode(lastDisplayMode);
            graphicsDevice.setFullScreenWindow(null);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Log.d(LOGCLASS, "destroying scene");
                scene.destroy();
                Log.d(LOGCLASS, "exiting");
                System.exit(0);
            }
        });
    }

    private void onEnteringFullScreen() {
        dispose();
        setUndecorated(true);

        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        lastDisplayMode = graphicsDevice.getDisplayMode();
        graphicsDevice.setFullScreenWindow(GameWindow.this);
        graphicsDevice.setDisplayMode(Settings.getDisplayMode().toAWTDisplayMode());

        setVisible(true);
    }

    private void onExitingFullScreen() {
        dispose();
        setUndecorated(false);

        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        // graphicsDevice.setDisplayMode(lastDisplayMode);
        graphicsDevice.setFullScreenWindow(null);
        setVisible(true);
        lastDisplayMode = null;
    }
}
