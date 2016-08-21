package sokoban.game.engine;

import sokoban.game.engine.scenes.Scene;
import sokoban.utils.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class GameWindow extends JFrame {
    private final static Class LOGCLASS = GameWindow.class;
    private Scene scene;

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

        if (scene != null) {
            Log.d(LOGCLASS, "preparing scene...");
            this.scene = scene;
            scene.setWindow(this);
            scene.onPrepare();
        }
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

    public final void showWindow() {
        if (scene == null)
            throw new IllegalStateException("have no scene");

        Log.d(LOGCLASS, "initialing scene");
        scene.Initialize();

        if (scene.getCanvas() == null) {
            throw new IllegalArgumentException("have not canvas");
        }
        getContentPane().add(scene.getCanvas());
        scene.getCanvas().setBounds(0, 0, getWidth(), getHeight());

        setVisible(true);

        scene.startRendering();
    }

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

    public final void close() {
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
}
