package sokoban.game.engine;

import sokoban.dialog.GameDialog;
import sokoban.dialog.LogDialog;
import sokoban.game.utils.FrameRateCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public abstract class GameWindow extends JFrame {
    private Scene scene;

    public GameWindow(Dimension size, String title, Scene scene) throws HeadlessException {
        if (scene != null) {
            this.scene = scene;
            scene.onPrepare();
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(size);
        setTitle("GameDialog");
        setIgnoreRepaint(true); // active rendering, need not passive repaint 主动渲染，无须被动渲染
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                scene.onDestroy();
            }
        });
    }
    public static void main(String[] args) {
        GameDialog dialog = new GameDialog();
        FrameRateCalculator frameRate = new FrameRateCalculator();
        frameRate.setShouldLog(true);
        dialog.setFrameRate(frameRate);

        LogDialog logDialog = LogDialog.getInstance();
        logDialog.setVisible(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dialog.createAndShowGUI();
                dialog.setVisible(true);
            }
        });
    }

    public final JFrame getWindow(){
        return this;
    }

    public final void showWindow() {
        if (scene==null)
            throw new IllegalStateException("Game window have no scene");

        scene.Initialize();
        add(scene.getCanvas());

        setVisible(true);
    }



    public final void switchScene(Scene newScene){
        if (isVisible()) {
            scene.onDestroy();
            newScene.onPrepare();
            newScene.onInitialize();
        }
        scene = newScene;
    }
}
