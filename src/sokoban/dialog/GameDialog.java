package sokoban.dialog;

import sokoban.game.engine.graphics.CoordinateSystemShower;
import sokoban.game.engine.graphics.Matrix3x3f;
import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.input.KeyboardInput;
import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.SimpleKeyboardInputHandler;
import sokoban.game.engine.input.handler.SimpleMouseInputHandler;
import sokoban.game.utils.FrameRateCalculator;
import sokoban.utils.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by CodeingBoy on 2016-7-10-0010.
 */
public class GameDialog extends JFrame implements Runnable {
    private FrameRateCalculator frameRate = null;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private Thread renderThread;
    private boolean rendering = false;
    private SimpleKeyboardInputHandler keyboardInputHandler;
    private SimpleMouseInputHandler mouseInputHandler;
    private float angel;
    private ArrayList<Point> points = new ArrayList();
    private float earthRot, moonRot;
    private ScreenMappingTool screenMappingTool;

    public GameDialog() throws HeadlessException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setTitle("GameDialog");
        setIgnoreRepaint(true); // active rendering, need not passive repaint 主动渲染，无须被动渲染
        setBackground(Color.gray);
        getContentPane().setBackground(Color.blue);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                onWindowClosing();
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

    private void onWindowClosing() {
        Log.d("stopping rendering...");
        rendering = false;
        try {
            renderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("render thread stopped!");

        Log.d("Exiting...");
        // TODO: Wait for Log dialog
        // try {
        //     LogDialog.getInstance().wait();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        System.exit(0);
    }

    public void createAndShowGUI() {
        canvas = new Canvas();
        canvas.setBackground(Color.black);
        add(canvas);
        setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        screenMappingTool = new ScreenMappingTool(5, 5, canvas);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                screenMappingTool = new ScreenMappingTool(5, 5, canvas);
            }
        });
        // addComponentListener(new ResizingHandler(canvas, getContentPane(), 4, 3, 0));

        KeyboardInput keyboardInput = new KeyboardInput();
        MouseInput mouseInput = new MouseInput();
        canvas.addKeyListener(keyboardInput);
        canvas.addMouseListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);
        canvas.addMouseWheelListener(mouseInput);
        keyboardInputHandler = new SimpleKeyboardInputHandler(keyboardInput);
        mouseInputHandler = new SimpleMouseInputHandler(mouseInput);

        renderThread = new Thread(this);
        renderThread.start();
    }

    public FrameRateCalculator getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(FrameRateCalculator frameRate) {
        if (frameRate != null && !frameRate.isInitalized())
            frameRate.initialize();
        this.frameRate = frameRate;
    }

    @Override
    public void run() {
        rendering = true;
        frameRate.initialize();

        long curTime = System.nanoTime();
        long lastTime = curTime;
        double nsPerSec;

        while (rendering) {
            curTime = System.nanoTime();
            nsPerSec = curTime - lastTime;
            lastTime = curTime;

            keyboardInputHandler.poll();
            keyboardInputHandler.processInput();
            mouseInputHandler.poll();
            mouseInputHandler.processInput();
            renderLoop(nsPerSec / 1.0E9);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void renderLoop(double delta) {
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

    private void render(Graphics g, double delta) {
        if (frameRate != null) {
            frameRate.calculate();
            g.setColor(Color.white);
            g.drawString(frameRate.getLatestFrameRateString(), 50, 50);
        }

        // CoordinateSystemShower.drawWorldXAxis(g, 0, 300, 50, canvas.getHeight(), true, 20);
        // CoordinateSystemShower.drawWorldYAxis(g, 0, 300, 50, canvas.getWidth(), true, 20);
        // System.out.println("in render " + canvas.getSize());

        CoordinateSystemShower.drawScreenXAxis(g, 0, 300, 50, canvas.getHeight(), screenMappingTool, 0);
        CoordinateSystemShower.drawScreenYAxis(g, 0, 300, 50, canvas.getWidth(), screenMappingTool, 0);

        // calc the square pos
        Matrix3x3f squMat = Matrix3x3f.translate(100, 100);
        squMat = squMat.mul(Matrix3x3f.rotate(angel));
        squMat = squMat.mul(Matrix3x3f.translate(200, 200));
        squMat = screenMappingTool.getScreenMatrix(squMat);
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


    // private class GamePanel extends JPanel {
    //
    //     @Override
    //     public void paint(Graphics g) {
    //         super.paint(g);
    //         onPaint(g);
    //         repaint();
    //     }
    //
    //     private void onPaint(Graphics g) {
    //         if (frameRate != null) {
    //             frameRate.calculate();
    //             g.setColor(Color.white);
    //             g.drawString(frameRate.getLatestFrameRateString(), 50, 50);
    //         }
    //     }
    // }
}
