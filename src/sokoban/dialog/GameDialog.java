package sokoban.dialog;

import sokoban.game.engine.GameWindow;
import sokoban.game.engine.graphics.CoordinateSystemShower;
import sokoban.game.engine.graphics.Matrix3x3f;
import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.scenes.EmptyScene;
import sokoban.game.engine.scenes.FrameRateScene;
import sokoban.game.engine.scenes.SuperScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by CodeigBoy on 2016-7-10-0010.
 */
public class GameDialog extends SuperScene implements Runnable {
    private SuperScene scene;
    private float angel;
    private float earthRot, moonRot;
    private ScreenMappingTool screenMappingTool;

    public GameDialog() {
    }

    public GameDialog(SuperScene scene) throws HeadlessException {
        super();
        this.scene = scene;
        // setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // setSize(500, 500);
        // setTitle("GameDialog");
        // setIgnoreRepaint(true); // active rendering, need not passive repaint 主动渲染，无须被动渲染
        // setBackground(Color.gray);
        // getContentPane().setBackground(Color.blue);
        // addWindowListener(new WindowAdapter() {
        //     @Override
        //     public void windowClosing(WindowEvent e) {
        //         super.windowClosing(e);
        //         onWindowClosing();
        //     }
        // });
    }

    public static void main(String[] args) {
        GameDialog scene = new GameDialog(new FrameRateScene(new EmptyScene(), 50, 50, Color.white, true));

        LogDialog logDialog = LogDialog.getInstance();
        logDialog.setVisible(true);

        GameWindow gameWindow = new GameWindow(new Dimension(500, 400), "GameDialog", scene);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameWindow.showWindow();
                // gameWindow.setVisible(true);
            }
        });
    }

    @Override
    public void beforeRendering() {
        scene.beforeRendering();
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onInitialize() {
        canvas = new Canvas();
        canvas.setBackground(Color.black);

        screenMappingTool = new ScreenMappingTool(5, 5, canvas);

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                screenMappingTool = new ScreenMappingTool(5, 5, canvas);
            }
        });

        // scene.onInitialize();
        // addComponentListener(new ResizingHandler(canvas, getContentPane(), 4, 3, 0));
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onExitingRendering() {

    }

    @Override
    public void afterTiming() {
        scene.afterTiming();
    }

    public void render(Graphics g, double delta) {
        scene.render(g, delta);
        // CoordinateSystemShower.drawScreenAxis(g, 0, 300, 50, canvas.getHeight(), 0);
        // CoordinateSystemShower.drawWorldYAxis(g, 0, 300, 50, canvas.getWidth(), 0);
        // System.out.println("in render " + canvas.getSize());

        CoordinateSystemShower.drawWorldXAxis(g, 0, 300, 50, canvas.getHeight(), screenMappingTool, 0);
        CoordinateSystemShower.drawWorldYAxis(g, 0, 300, 50, canvas.getWidth(), screenMappingTool, 0);

        // calc the square pos
        Matrix3x3f squMat = Matrix3x3f.translate(100, 100);

        squMat = squMat.mul(Matrix3x3f.rotate(angel));
        squMat = squMat.mul(Matrix3x3f.translate(200, 200));
        squMat = screenMappingTool.worldToScreen(squMat);
        angel += Math.toRadians(0.03);
        Vector2f squVec = squMat.mul(new Vector2f(0, 0, 1));

        // draw the square
        g.setColor(Color.green);
        g.fillRect((int) squVec.x - 10, (int) squVec.y - 10, 20, 20);

        // calc the sun pos
        Matrix3x3f sunMat = Matrix3x3f.translate(screenMappingTool.worldToScreen(new Point(0, 0)));
        Vector2f sunVec = sunMat.toVector();

        // System.out.println(screenMappingTool.worldToScreen(new Point(0, 0)));

        // draw the sun
        g.setColor(Color.yellow);
        g.fillOval((int) sunVec.x - 50, (int) sunVec.y - 50, 100, 100);

        // draw the orbit
        g.setColor(Color.white);
        g.drawOval((int) sunVec.x - canvas.getWidth() / 4, (int) sunVec.y - canvas.getWidth() / 4,
                canvas.getWidth() / 2, canvas.getWidth() / 2);

        // calc the earth pos
        Matrix3x3f earthMat = Matrix3x3f.translate(canvas.getWidth() / 4, 0);
        earthMat = earthMat.mul(Matrix3x3f.rotate(earthRot));
        earthMat = earthMat.mul(sunMat);
        earthRot += Math.toRadians(180) * delta;
        Vector2f earthVec = earthMat.toVector();

        // draw the earth
        g.setColor(Color.blue);
        g.fillOval((int) earthVec.x - 10, (int) earthVec.y - 10, 20, 20);

        // calc the moon pos
        Matrix3x3f moonMat = Matrix3x3f.translate(30, 0);
        moonMat = moonMat.mul(Matrix3x3f.rotate(moonRot));
        moonMat = moonMat.mul(earthMat);
        moonRot += Math.toRadians(2.5);
        Vector2f moonVec = moonMat.toVector();

        // draw the moon
        g.setColor(Color.gray);
        g.fillOval((int) moonVec.x - 5, (int) moonVec.y - 5, 10, 10);

    }
}
