package sokoban.game.engine;

import sokoban.game.engine.scenes.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class GameWindow extends JFrame {
    private Scene scene;
    private Scene newScene;

    public GameWindow(Dimension size, String title, Scene scene) throws HeadlessException {
        if (scene != null) {
            this.scene = scene;
            scene.setWindow(this);
            scene.onPrepare();
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(size);
        setTitle(title);
        setIgnoreRepaint(true); // active rendering, need not passive repaint 主动渲染，无须被动渲染
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                scene.destroy();
                System.exit(0);
            }
        });
    }
    // public static void main(String[] args) {
    //     GameDialog dialog = new GameDialog();
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
            throw new IllegalStateException("Game window have no scene");

        scene.Initialize();

        if (scene.getCanvas() == null) {
            throw new IllegalArgumentException("have not canvas");
        }
        add(scene.getCanvas());

        setVisible(true);

        scene.startRendering();
    }

    public final void switchScene(Scene newScene) {
        if (isVisible()) {
            scene.destroy();
            scene.setWindow(null);
            remove(scene.getCanvas()); // 移除旧场景的画布 否则无法显示
            newScene.setWindow(this);
            newScene.onPrepare();
            scene = newScene;
            showWindow();
        }
    }
}
